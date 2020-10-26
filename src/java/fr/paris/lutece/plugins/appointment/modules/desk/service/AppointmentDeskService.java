package fr.paris.lutece.plugins.appointment.modules.desk.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.locks.Lock;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.appointment.business.slot.Slot;
import fr.paris.lutece.plugins.appointment.modules.desk.util.AppointmentDeskPlugin;
import fr.paris.lutece.plugins.appointment.modules.desk.util.IncrementSlot;
import fr.paris.lutece.plugins.appointment.modules.desk.util.IncrementingType;
import fr.paris.lutece.plugins.appointment.service.SlotSafeService;
import fr.paris.lutece.plugins.appointment.service.SlotService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.sql.TransactionManager;

public class AppointmentDeskService
{

  
	private AppointmentDeskService( ){
		
	}
	
   
    public static void closeAppointmentDesk( List<Slot> listSlot ) {
        
    
    	for(Slot slot: listSlot) {
    		
    		if( slot.getIdSlot() != 0) 
    		{
    			 // Need to get all the informations to create the slot
	        
    			SlotService.addDateAndTimeToSlot(slot);
	            SlotSafeService.saveSlot(slot);
    			
    		}
    		Lock lock = SlotSafeService.getLockOnSlot( slot.getIdSlot() );
    		lock.lock( );
    		try
    		{
    		    Slot oldSlot = SlotService.findSlotById( slot.getIdSlot( ) );
    	        slot.setMaxCapacity(oldSlot.getMaxCapacity() - 1);
    	        slot.setNbPotentialRemainingPlaces( Math.max( 0, oldSlot.getNbPotentialRemainingPlaces( ) - 1 ) );
    	        slot.setNbRemainingPlaces( oldSlot.getNbRemainingPlaces( ) - 1  );
	
    		     TransactionManager.beginTransaction( AppointmentDeskPlugin.getPlugin( ) );  
    	        	
    		     SlotSafeService.saveSlot( slot );
    		     TransactionManager.commitTransaction( AppointmentDeskPlugin.getPlugin( ) );
    		        
    		    }catch( Exception e )
    		    {
    		    	TransactionManager.rollBack( AppointmentDeskPlugin.getPlugin( ) );
    		    	AppLogService.error( "Error close appointment desk" + e.getMessage(), e );
    		    	
    		    }finally
		        {

		            lock.unlock( );
		        }

    		
    	}
    

    }
    
    
    public static void openAppointmentDesk( List<Slot> listSlot ) {
        
    
    	for(Slot slot: listSlot) {
    		
    		if( slot.getIdSlot() != 0) 
    		{
    			 // Need to get all the informations to create the slot
	        
    			SlotService.addDateAndTimeToSlot(slot);
	            SlotSafeService.saveSlot(slot);
    			
    		}
    		Lock lock = SlotSafeService.getLockOnSlot( slot.getIdSlot() );
    		lock.lock( );
    		try
    		{
    		    Slot oldSlot = SlotService.findSlotById( slot.getIdSlot( ) );

    		   slot.setMaxCapacity(oldSlot.getMaxCapacity() + 1);
 		       slot.setNbPotentialRemainingPlaces( oldSlot.getNbPotentialRemainingPlaces( ) + 1 );
    		   slot.setNbRemainingPlaces( oldSlot.getNbRemainingPlaces( ) + 1 );

    	       slot.setIsOpen( true );
	
    		   TransactionManager.beginTransaction( AppointmentDeskPlugin.getPlugin( ) );  
    	        	
    		     SlotSafeService.saveSlot( slot );
    		    TransactionManager.commitTransaction( AppointmentDeskPlugin.getPlugin( ) );
    		        
    		    }catch( Exception e )
    		    {
    		    	TransactionManager.rollBack( AppointmentDeskPlugin.getPlugin( ) );
    		    	AppLogService.error( "Error open appointment desk" + e.getMessage(), e );
    		    	
    		    }finally
		        {

		            lock.unlock( );
		        }

    		
    	}
    

    }
    
    
    public static void incrementMaxCapacity ( IncrementSlot incrementSlot ) {
    
    	LocalDateTime startingDateTimes;
    	LocalDateTime endingDateTimes;
    	boolean lace= false;
    	
    	if ( StringUtils.isNotEmpty( incrementSlot.getStartingTime() ) &&   IncrementingType.HALFTIMEMORNING.getValue() != incrementSlot.getType( ).getValue( ))
        {
    		startingDateTimes = incrementSlot.getStartingDate().atTime( LocalTime.parse( incrementSlot.getStartingTime( ) ) );
        }
        else
        {
        	startingDateTimes = incrementSlot.getStartingDate().atStartOfDay( ) ;
        }
    	
    	if ( StringUtils.isNotEmpty( incrementSlot.getEndingTime( ) ) &&  IncrementingType.HALFTIMEAFTERNOON.getValue( ) != incrementSlot.getType( ).getValue( ))
        {
    		endingDateTimes = incrementSlot.getStartingDate().atTime( LocalTime.parse( incrementSlot.getEndingTime( ) ) );
        }
        else
        {
        	endingDateTimes = incrementSlot.getStartingDate().atTime( LocalTime.MAX ) ;
        }
    	
    	if( incrementSlot.getType().getValue() == IncrementingType.LACE.getValue( )) {
    		lace= true;
    	}
    	
    	SlotSafeService.incrementMaxCapacity( incrementSlot.getIdForm( ), incrementSlot.getIncrementingValue( ), startingDateTimes, endingDateTimes, lace );
    } 
}
