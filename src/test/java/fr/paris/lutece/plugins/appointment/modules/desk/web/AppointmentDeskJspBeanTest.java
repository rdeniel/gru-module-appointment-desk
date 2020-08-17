
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
 * SUBSTITUTE GOODS OR SERVICES LOSS OF USE, DATA, OR PROFITS OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */

package fr.paris.lutece.plugins.appointment-desk.web;

import fr.paris.lutece.plugins.appointment-desk.business.AppointmentDesk;
import fr.paris.lutece.plugins.appointment-desk.business.AppointmentDeskHome;
import java.util.List;
import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.portal.web.LocalVariables;
import fr.paris.lutece.portal.service.security.SecurityTokenService;
import java.sql.Date;
import fr.paris.lutece.util.date.DateUtil;
import fr.paris.lutece.portal.web.l10n.LocaleService;
import java.io.IOException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.admin.AdminAuthenticationService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;

/**
 * This is the business class test for the object AppointmentDesk
 */
public class AppointmentDeskJspBeanTest extends LuteceTestCase
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

    public void testJspBeans( ) throws AccessDeniedException
    {
        MockHttpServletRequest request = new MockHttpServletRequest( );
        MockHttpServletResponse response = new MockHttpServletResponse( );
        MockServletConfig config = new MockServletConfig( );

        // display admin AppointmentDesk management JSP
        AppointmentDeskJspBean jspbean = new AppointmentDeskJspBean( );
        String html = jspbean.getManageAppointmentDesks( request );
        assertNotNull( html );

        // display admin AppointmentDesk creation JSP
        html = jspbean.getCreateAppointmentDesk( request );
        assertNotNull( html );

        // action create AppointmentDesk
        request = new MockHttpServletRequest( );

        request.addParameter( "id_form", String.valueOf( IDFORM1 ) );
        request.addParameter( "day", DateUtil.getDateString( DAY1, LocaleService.getDefault( ) ) );
        request.addParameter( "desk_number", String.valueOf( DESKNUMBER1 ) );
        request.addParameter( "start_cloding", DateUtil.getDateString( STARTCLODING1, LocaleService.getDefault( ) ) );
        request.addParameter( "end_closing", DateUtil.getDateString( ENDCLOSING1, LocaleService.getDefault( ) ) );
        request.addParameter( "action", "createAppointmentDesk" );
        request.addParameter( "token", SecurityTokenService.getInstance( ).getToken( request, "createAppointmentDesk" ) );
        request.setMethod( "POST" );
        response = new MockHttpServletResponse( );
        AdminUser adminUser = new AdminUser( );
        adminUser.setAccessCode( "admin" );

        try
        {
            AdminAuthenticationService.getInstance( ).registerUser( request, adminUser );
            html = jspbean.processController( request, response );

            // MockResponse object does not redirect, result is always null
            assertNull( html );
        }
        catch( AccessDeniedException e )
        {
            fail( "access denied" );
        }
        catch( UserNotSignedException e )
        {
            fail( "user not signed in" );
        }

        // display modify AppointmentDesk JSP
        request = new MockHttpServletRequest( );
        request.addParameter( "id_form", String.valueOf( IDFORM1 ) );
        request.addParameter( "day", DateUtil.getDateString( DAY1, LocaleService.getDefault( ) ) );
        request.addParameter( "desk_number", String.valueOf( DESKNUMBER1 ) );
        request.addParameter( "start_cloding", DateUtil.getDateString( STARTCLODING1, LocaleService.getDefault( ) ) );
        request.addParameter( "end_closing", DateUtil.getDateString( ENDCLOSING1, LocaleService.getDefault( ) ) );
        List<Integer> listIds = AppointmentDeskHome.getIdAppointmentDesksList( );
        assertTrue( !listIds.isEmpty( ) );
        request.addParameter( "id", String.valueOf( listIds.get( 0 ) ) );
        jspbean = new AppointmentDeskJspBean( );

        assertNotNull( jspbean.getModifyAppointmentDesk( request ) );

        // action modify AppointmentDesk
        request = new MockHttpServletRequest( );
        response = new MockHttpServletResponse( );
        request.addParameter( "id_form", String.valueOf( IDFORM2 ) );
        request.addParameter( "day", DateUtil.getDateString( DAY2, LocaleService.getDefault( ) ) );
        request.addParameter( "desk_number", String.valueOf( DESKNUMBER2 ) );
        request.addParameter( "start_cloding", DateUtil.getDateString( STARTCLODING2, LocaleService.getDefault( ) ) );
        request.addParameter( "end_closing", DateUtil.getDateString( ENDCLOSING2, LocaleService.getDefault( ) ) );
        request.setRequestURI( "jsp/admin/plugins/example/ManageAppointmentDesks.jsp" );
        // important pour que MVCController sache quelle action effectuer, sinon, il redirigera vers createAppointmentDesk, qui est l'action par défaut
        request.addParameter( "action", "modifyAppointmentDesk" );
        request.addParameter( "token", SecurityTokenService.getInstance( ).getToken( request, "modifyAppointmentDesk" ) );
        adminUser = new AdminUser( );
        adminUser.setAccessCode( "admin" );

        try
        {
            AdminAuthenticationService.getInstance( ).registerUser( request, adminUser );
            html = jspbean.processController( request, response );

            // MockResponse object does not redirect, result is always null
            assertNull( html );
        }
        catch( AccessDeniedException e )
        {
            fail( "access denied" );
        }
        catch( UserNotSignedException e )
        {
            fail( "user not signed in" );
        }

        // get remove AppointmentDesk
        request = new MockHttpServletRequest( );
        // request.setRequestURI("jsp/admin/plugins/example/ManageAppointmentDesks.jsp");
        request.addParameter( "id", String.valueOf( listIds.get( 0 ) ) );
        jspbean = new AppointmentDeskJspBean( );
        request.addParameter( "action", "confirmRemoveAppointmentDesk" );
        assertNotNull( jspbean.getModifyAppointmentDesk( request ) );

        // do remove AppointmentDesk
        request = new MockHttpServletRequest( );
        response = new MockHttpServletResponse( );
        request.setRequestURI( "jsp/admin/plugins/example/ManageAppointmentDeskts.jsp" );
        // important pour que MVCController sache quelle action effectuer, sinon, il redirigera vers createAppointmentDesk, qui est l'action par défaut
        request.addParameter( "action", "removeAppointmentDesk" );
        request.addParameter( "token", SecurityTokenService.getInstance( ).getToken( request, "removeAppointmentDesk" ) );
        request.addParameter( "id", String.valueOf( listIds.get( 0 ) ) );
        request.setMethod( "POST" );
        adminUser = new AdminUser( );
        adminUser.setAccessCode( "admin" );

        try
        {
            AdminAuthenticationService.getInstance( ).registerUser( request, adminUser );
            html = jspbean.processController( request, response );

            // MockResponse object does not redirect, result is always null
            assertNull( html );
        }
        catch( AccessDeniedException e )
        {
            fail( "access denied" );
        }
        catch( UserNotSignedException e )
        {
            fail( "user not signed in" );
        }

    }
}
