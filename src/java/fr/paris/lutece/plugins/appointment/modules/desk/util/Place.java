package fr.paris.lutece.plugins.appointment.modules.desk.util;

import fr.paris.lutece.plugins.appointment.business.appointment.Appointment;
import fr.paris.lutece.plugins.appointment.business.slot.Slot;

public class Place
{

    // Variables declarations
    private int _nNumberDesk;
    private Appointment _appointment;
    private Slot _slot;
    private boolean _bIsOpen = true;
    private boolean _bIsSurbooking;
    private int _nIdAppointmentDesk;

    /**
     * Returns the NumberDesk
     * 
     * @return The NumberDesk
     */
    public int getNumberDesk( )
    {
        return _nNumberDesk;
    }

    /**
     * Sets the NumberDesk
     * 
     * @param nNumberDesk
     *            The NumberDesk
     */
    public void setNumberDesk( int nNumberDesk )
    {
        _nNumberDesk = nNumberDesk;
    }

    /**
     * Returns the Appointment
     * 
     * @return The Appointment
     */
    public Appointment getAppointment( )
    {
        return _appointment;
    }

    /**
     * Sets the Appointment
     * 
     * @param appointment
     *            The Appointment
     */
    public void setAppointment( Appointment appointment )
    {
        _appointment = appointment;
    }

    /**
     * Returns the Slot
     * 
     * @return The Slot
     */
    public Slot getSlot( )
    {
        return _slot;
    }

    /**
     * Sets the Slot
     * 
     * @param slot
     *            The Slot
     */
    public void setSlot( Slot slot )
    {
        _slot = slot;
    }

    /**
     * Returns the IsOpen
     * 
     * @return The IsOpen
     */
    public boolean getIsOpen( )
    {
        return _bIsOpen;
    }

    /**
     * Sets the IsOpen
     * 
     * @param bIsOpen
     *            The IsOpen
     */
    public void setIsOpen( boolean bIsOpen )
    {
        _bIsOpen = bIsOpen;
    }

    /**
     * Returns the IsSurbooking
     * 
     * @return The IsSurbooking
     */
    public boolean getIsSurbooking( )
    {
        return _bIsSurbooking;
    }

    /**
     * Sets the IsSurbooking
     * 
     * @param dIsSurbooking
     *            The IsSurbooking
     */
    public void setIsSurbooking( boolean dIsSurbooking )
    {
        _bIsSurbooking = dIsSurbooking;
    }

    /**
     * Returns the IdAppointmentDesk
     * 
     * @return The IdAppointmentDesk
     */
    public int getIdAppointmentDesk( )
    {
        return _nIdAppointmentDesk;
    }

    /**
     * Sets the IdAppointmentDesk
     * 
     * @param nIdAppointmentDesk
     *            The IdAppointmentDesk
     */
    public void setIdAppointmentDesk( int nIdAppointmentDesk )
    {
        _nIdAppointmentDesk = nIdAppointmentDesk;
    }

}
