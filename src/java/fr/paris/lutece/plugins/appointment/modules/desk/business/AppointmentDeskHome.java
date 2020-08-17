
/*
 * Copyright (c) 2002-2020, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.appointment.modules.desk.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.time.LocalDate;
import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for AppointmentDesk objects
 */
public final class AppointmentDeskHome
{
    // Static variable pointed at the DAO instance
    private static IAppointmentDeskDAO _dao = SpringContextService.getBean( "appointment-desk.appointmentDeskDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "appointment-desk" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private AppointmentDeskHome( )
    {
    }

    /**
     * Create an instance of the appointmentDesk class
     * 
     * @param appointmentDesk
     *            The instance of the AppointmentDesk which contains the informations to store
     * @return The instance of appointmentDesk which has been created with its primary key.
     */
    public static AppointmentDesk create( AppointmentDesk appointmentDesk )
    {
        _dao.insert( appointmentDesk, _plugin );

        return appointmentDesk;
    }

    /**
     * Update of the appointmentDesk which is specified in parameter
     * 
     * @param appointmentDesk
     *            The instance of the AppointmentDesk which contains the data to store
     * @return The instance of the appointmentDesk which has been updated
     */
    public static AppointmentDesk update( AppointmentDesk appointmentDesk )
    {
        _dao.store( appointmentDesk, _plugin );

        return appointmentDesk;
    }

    /**
     * Remove the appointmentDesk whose identifier is specified in parameter
     * 
     * @param nKey
     *            The appointmentDesk Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a appointmentDesk whose identifier is specified in parameter
     * 
     * @param nKey
     *            The appointmentDesk primary key
     * @return an instance of AppointmentDesk
     */
    public static AppointmentDesk findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Load the data of all the appointmentDesk objects and returns them as a list
     * 
     * @return the list which contains the data of all the appointmentDesk objects
     */
    public static List<AppointmentDesk> getAppointmentDesksList( )
    {
        return _dao.selectAppointmentDesksList( _plugin );
    }

    /**
     * Load the id of all the appointmentDesk objects and returns them as a list
     * 
     * @return the list which contains the id of all the appointmentDesk objects
     */
    public static List<Integer> getIdAppointmentDesksList( )
    {
        return _dao.selectIdAppointmentDesksList( _plugin );
    }

    /**
     * Load the data of all the appointmentDesk objects and returns them as a referenceList
     * 
     * @return the referenceList which contains the data of all the appointmentDesk objects
     */
    public static ReferenceList getAppointmentDesksReferenceList( )
    {
        return _dao.selectAppointmentDesksReferenceList( _plugin );
    }

    /**
     * Load the data of all the appointmentDesk objects and returns them as a list
     * 
     * @param day
     *            the day
     * @param idForm
     *            the id form
     * @return The list which contains the data of all the appointmentDesk objects
     */

    public static List<AppointmentDesk> selectAppointmentDesksList( LocalDate day, int idForm )
    {
        return _dao.selectAppointmentDesksList( day, idForm, _plugin );
    }

}
