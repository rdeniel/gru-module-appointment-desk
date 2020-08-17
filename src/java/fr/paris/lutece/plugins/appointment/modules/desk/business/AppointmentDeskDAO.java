
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
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;

import java.sql.Date;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for AppointmentDesk objects
 */
public final class AppointmentDeskDAO implements IAppointmentDeskDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_appointment_desk, id_form, day, desk_number, start_closing, end_closing FROM appointment_desk_day WHERE id_appointment_desk = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appointment_desk_day ( id_form, day, desk_number, start_closing, end_closing ) VALUES ( ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appointment_desk_day WHERE id_appointment_desk = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appointment_desk_day SET id_appointment_desk = ?, id_form = ?, day = ?, desk_number = ?, start_closing = ?, end_closing = ? WHERE id_appointment_desk = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_appointment_desk, id_form, day, desk_number, start_closing, end_closing FROM appointment_desk_day";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_appointment_desk FROM appointment_desk_day";
    private static final String SQL_QUERY_SELECT_BY_DATE = "SELECT id_appointment_desk, id_form, day, desk_number, start_closing, end_closing FROM appointment_desk_day where day=? and id_form=?";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( AppointmentDesk appointmentDesk, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setInt( nIndex++, appointmentDesk.getIdForm( ) );
            daoUtil.setDate( nIndex++, appointmentDesk.getSqlDateDay( ) );
            daoUtil.setInt( nIndex++, appointmentDesk.getDeskNumber( ) );
            daoUtil.setTime( nIndex++, appointmentDesk.getStartingTimeSqlTime( ) );
            daoUtil.setTime( nIndex++, appointmentDesk.getEndingTimeSqlTime( ) );

            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                appointmentDesk.setIdAppointmentDesk( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public AppointmentDesk load( int nKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
            daoUtil.setInt( 1, nKey );
            daoUtil.executeQuery( );
            AppointmentDesk appointmentDesk = null;

            if ( daoUtil.next( ) )
            {
                appointmentDesk = new AppointmentDesk( );
                int nIndex = 1;

                appointmentDesk.setIdAppointmentDesk( daoUtil.getInt( nIndex++ ) );
                appointmentDesk.setIdForm( daoUtil.getInt( nIndex++ ) );
                appointmentDesk.setSqlDateDay( daoUtil.getDate( nIndex++ ) );
                appointmentDesk.setDeskNumber( daoUtil.getInt( nIndex++ ) );
                appointmentDesk.setSqlStartingTime( daoUtil.getTime( nIndex++ ) );
                appointmentDesk.setSqlEndingTime( daoUtil.getTime( nIndex ) );
            }

            daoUtil.free( );
            return appointmentDesk;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin ) )
        {
            daoUtil.setInt( 1, nKey );
            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( AppointmentDesk appointmentDesk, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
            int nIndex = 1;

            daoUtil.setInt( nIndex++, appointmentDesk.getIdAppointmentDesk( ) );
            daoUtil.setInt( nIndex++, appointmentDesk.getIdForm( ) );
            daoUtil.setDate( nIndex++, appointmentDesk.getSqlDateDay( ) );
            daoUtil.setInt( nIndex++, appointmentDesk.getDeskNumber( ) );
            daoUtil.setTime( nIndex++, appointmentDesk.getStartingTimeSqlTime( ) );
            daoUtil.setTime( nIndex++, appointmentDesk.getEndingTimeSqlTime( ) );
            daoUtil.setInt( nIndex, appointmentDesk.getIdAppointmentDesk( ) );

            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<AppointmentDesk> selectAppointmentDesksList( Plugin plugin )
    {
        List<AppointmentDesk> appointmentDeskList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                AppointmentDesk appointmentDesk = new AppointmentDesk( );
                int nIndex = 1;

                appointmentDesk.setIdAppointmentDesk( daoUtil.getInt( nIndex++ ) );
                appointmentDesk.setIdForm( daoUtil.getInt( nIndex++ ) );
                appointmentDesk.setSqlDateDay( daoUtil.getDate( nIndex++ ) );
                appointmentDesk.setDeskNumber( daoUtil.getInt( nIndex++ ) );
                appointmentDesk.setSqlStartingTime( daoUtil.getTime( nIndex++ ) );
                appointmentDesk.setSqlEndingTime( daoUtil.getTime( nIndex ) );

                appointmentDeskList.add( appointmentDesk );
            }

            daoUtil.free( );
            return appointmentDeskList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdAppointmentDesksList( Plugin plugin )
    {
        List<Integer> appointmentDeskList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                appointmentDeskList.add( daoUtil.getInt( 1 ) );
            }

            daoUtil.free( );
            return appointmentDeskList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectAppointmentDesksReferenceList( Plugin plugin )
    {
        ReferenceList appointmentDeskList = new ReferenceList( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                appointmentDeskList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
            }

            daoUtil.free( );
            return appointmentDeskList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<AppointmentDesk> selectAppointmentDesksList( LocalDate day, int idForm, Plugin plugin )
    {
        List<AppointmentDesk> appointmentDeskList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_DATE, plugin ) )
        {
            daoUtil.setDate( 1, Date.valueOf( day ) );
            daoUtil.setInt( 2, idForm );
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                AppointmentDesk appointmentDesk = new AppointmentDesk( );
                int nIndex = 1;

                appointmentDesk.setIdAppointmentDesk( daoUtil.getInt( nIndex++ ) );
                appointmentDesk.setIdForm( daoUtil.getInt( nIndex++ ) );
                appointmentDesk.setSqlDateDay( daoUtil.getDate( nIndex++ ) );
                appointmentDesk.setDeskNumber( daoUtil.getInt( nIndex++ ) );
                appointmentDesk.setSqlStartingTime( daoUtil.getTime( nIndex++ ) );
                appointmentDesk.setSqlEndingTime( daoUtil.getTime( nIndex ) );

                appointmentDeskList.add( appointmentDesk );
            }

            daoUtil.free( );
            return appointmentDeskList;
        }
    }

}
