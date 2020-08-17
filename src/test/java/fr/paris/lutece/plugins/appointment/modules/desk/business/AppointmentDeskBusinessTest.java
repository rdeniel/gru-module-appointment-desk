
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
 *"
 * License 1.0
 */

package fr.paris.lutece.plugins.appointment-desk.business;

import fr.paris.lutece.test.LuteceTestCase;

import java.sql.Date;

/**
 * This is the business class test for the object AppointmentDesk
 */
public class AppointmentDeskBusinessTest extends LuteceTestCase
{
    private static final int IDFORM1 = 1;
    private static final int IDFORM2 = 2;
    private static final Date DAY1 = new Date( 1000000l );
    private static final Date DAY2 = new Date( 2000000l );
    private static final int DESKNUMBER1 = 1;
    private static final int DESKNUMBER2 = 2;
    private static final Date STARTCLODING1 = new Date( 1000000l );
    private static final Date STARTCLODING2 = new Date( 2000000l );
    private static final Date ENDCLOSING1 = new Date( 1000000l );
    private static final Date ENDCLOSING2 = new Date( 2000000l );

    /**
     * test AppointmentDesk
     */
    public void testBusiness( )
    {
        // Initialize an object
        AppointmentDesk appointmentDesk = new AppointmentDesk( );
        appointmentDesk.setIdForm( IDFORM1 );
        appointmentDesk.setDay( DAY1 );
        appointmentDesk.setDeskNumber( DESKNUMBER1 );
        appointmentDesk.setStartCloding( STARTCLODING1 );
        appointmentDesk.setEndClosing( ENDCLOSING1 );

        // Create test
        AppointmentDeskHome.create( appointmentDesk );
        AppointmentDesk appointmentDeskStored = AppointmentDeskHome.findByPrimaryKey( appointmentDesk.getId( ) );
        assertEquals( appointmentDeskStored.getIdForm( ), appointmentDesk.getIdForm( ) );
        assertEquals( appointmentDeskStored.getDay( ).toString( ), appointmentDesk.getDay( ).toString( ) );
        assertEquals( appointmentDeskStored.getDeskNumber( ), appointmentDesk.getDeskNumber( ) );
        assertEquals( appointmentDeskStored.getStartCloding( ).toString( ), appointmentDesk.getStartCloding( ).toString( ) );
        assertEquals( appointmentDeskStored.getEndClosing( ).toString( ), appointmentDesk.getEndClosing( ).toString( ) );

        // Update test
        appointmentDesk.setIdForm( IDFORM2 );
        appointmentDesk.setDay( DAY2 );
        appointmentDesk.setDeskNumber( DESKNUMBER2 );
        appointmentDesk.setStartCloding( STARTCLODING2 );
        appointmentDesk.setEndClosing( ENDCLOSING2 );
        AppointmentDeskHome.update( appointmentDesk );
        appointmentDeskStored = AppointmentDeskHome.findByPrimaryKey( appointmentDesk.getId( ) );
        assertEquals( appointmentDeskStored.getIdForm( ), appointmentDesk.getIdForm( ) );
        assertEquals( appointmentDeskStored.getDay( ).toString( ), appointmentDesk.getDay( ).toString( ) );
        assertEquals( appointmentDeskStored.getDeskNumber( ), appointmentDesk.getDeskNumber( ) );
        assertEquals( appointmentDeskStored.getStartCloding( ).toString( ), appointmentDesk.getStartCloding( ).toString( ) );
        assertEquals( appointmentDeskStored.getEndClosing( ).toString( ), appointmentDesk.getEndClosing( ).toString( ) );

        // List test
        AppointmentDeskHome.getAppointmentDesksList( );

        // Delete test
        AppointmentDeskHome.remove( appointmentDesk.getId( ) );
        appointmentDeskStored = AppointmentDeskHome.findByPrimaryKey( appointmentDesk.getId( ) );
        assertNull( appointmentDeskStored );

    }

}
