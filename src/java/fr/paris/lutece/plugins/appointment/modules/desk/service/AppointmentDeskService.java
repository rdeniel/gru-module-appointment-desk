package fr.paris.lutece.plugins.appointment.modules.desk.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.paris.lutece.plugins.appointment.business.appointment.Appointment;
import fr.paris.lutece.plugins.appointment.business.slot.Slot;
import fr.paris.lutece.plugins.appointment.business.user.UserHome;
import fr.paris.lutece.plugins.appointment.modules.desk.business.AppointmentDesk;
import fr.paris.lutece.plugins.appointment.modules.desk.business.AppointmentDeskHome;
import fr.paris.lutece.plugins.appointment.modules.desk.util.Place;
import fr.paris.lutece.plugins.appointment.service.AppointmentService;

public class AppointmentDeskService
{

  
	private AppointmentDeskService( ){
		
	}
	
    public static List<Place> builListdPlace( List<Slot> listSlot, int nIdForm, LocalDate day, int numberDesk )
    {

        List<Place> listPlace = new ArrayList<>( );
        List<AppointmentDesk> apptDesk = AppointmentDeskHome.selectAppointmentDesksList( day, nIdForm );

        for ( Slot slot : listSlot )
        {

            int index = numberDesk;
            List<Appointment> listAppt = new ArrayList<>( );
            if ( slot.getIdSlot( ) != 0 )
            {

                listAppt = AppointmentService.findListAppointmentBySlot( slot.getIdSlot( ) );
                for ( Appointment appt : listAppt )
                {

                    appt.setUser( UserHome.findByPrimaryKey( appt.getIdUser( ) ) );

                }
            }
            if ( index < listAppt.size( ) )
            {

                index = listAppt.size( );
            }
            int i = 1;
            int k = 0;
            while ( i <= index )
            {
                int j = i;

                Place place = new Place( );
                place.setSlot( slot );
                Appointment appt = null;
                List<AppointmentDesk> listApptDesk = apptDesk.stream( )
                        .filter( p -> p.getStartClosing( ).isBefore( slot.getStartingTime( ).plusMinutes( 1 ) )
                                && p.getEndClosing( ).isAfter( slot.getEndingTime( ).minusMinutes( 1 ) ) && p.getDeskNumber( ) == j )
                        .collect( Collectors.toList( ) );
                if ( listApptDesk != null && !listApptDesk.isEmpty( ) )
                {

                    place.setIsOpen( false );
                    place.setIdAppointmentDesk( listApptDesk.get( 0 ).getIdAppointmentDesk( ) );
                    index = index + 1;
                }
                if ( listAppt.size( ) > k && place.getIsOpen( ) )
                {

                    appt = listAppt.get( k );
                    k++;
                }
                if ( i > numberDesk )
                {

                    place.setIsSurbooking( true );
                }
                place.setAppointment( appt );
                place.setNumberDesk( j );
                listPlace.add( place );
                i++;
            }

        }
        return listPlace;
    }

}
