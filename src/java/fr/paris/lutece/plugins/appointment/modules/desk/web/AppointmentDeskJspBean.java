
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

package fr.paris.lutece.plugins.appointment.modules.desk.web;

import fr.paris.lutece.plugins.appointment.business.appointment.Appointment;
import fr.paris.lutece.plugins.appointment.business.comment.Comment;
import fr.paris.lutece.plugins.appointment.business.planning.WeekDefinition;
import fr.paris.lutece.plugins.appointment.business.slot.Period;
import fr.paris.lutece.plugins.appointment.business.slot.Slot;
import fr.paris.lutece.plugins.appointment.modules.desk.business.AppointmentDesk;
import fr.paris.lutece.plugins.appointment.modules.desk.business.AppointmentDeskHome;
import fr.paris.lutece.plugins.appointment.modules.desk.service.AppointmentDeskService;
import fr.paris.lutece.plugins.appointment.modules.desk.util.Place;
import fr.paris.lutece.plugins.appointment.service.AppointmentService;
import fr.paris.lutece.plugins.appointment.service.CommentService;
import fr.paris.lutece.plugins.appointment.service.SlotService;
import fr.paris.lutece.plugins.appointment.service.WeekDefinitionService;
import fr.paris.lutece.plugins.appointment.web.dto.AppointmentFilterDTO;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.date.DateUtil;
import fr.paris.lutece.util.url.UrlItem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * This class provides the user interface to manage AppointmentDesk features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageAppointmentDesks.jsp", controllerPath = "jsp/admin/plugins/appointment/modules/desk/", right = "APPOINTMENT_DESK_MANAGEMENT" )
public class AppointmentDeskJspBean extends AbstractManageAppointmentDeskJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_APPOINTMENTDESKS = "/admin/plugins/appointment/modules/desk/manage_appointmentdesks.html";

    // Parameters
    private static final String PARAMETER_ID_APPOINTMENTDESK = "id";
    private static final String PARAMETER_ID_FORM = "id_form";
    private static final String PARAMETER_LIST_PLACE = "places";

    private static final String PARAMETER_OPENING_TIME = "start_closing";
    private static final String PARAMETER_CLOSING_TIME = "end_closing";
    private static final String PARAMETER_NUMB_DESK = "numb_desk";
    private static final String PARAMETER_DATE_DAY = "day";
    private static final String PARAMETER_STARTING_DATE_TIME = "starting_date_time";
    private static final String PARAMETER_ENDING_DATE_TIME = "ending_date_time";
    private static final String PARAMETER_IS_OPEN = "is_open";
    private static final String PARAMETER_IS_SPECIFIC = "is_specific";
    private static final String PARAMETER_MAX_CAPACITY = "max_capacity";
    private static final String PARAMETER_ID_SLOT = "id_slot";


    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_APPOINTMENTDESKS = "appointment-desk.manage_appointmentdesks.pageTitle";

    // Markers
    private static final String MARK_APPOINTMENTDESK_LIST = "appointmentdesk_list";
    private static final String MARK_LOCALE = "language";
    private static final String MARK_DATE_DAY = "day";
    private static final String MARK_ID_FORM = "idForm";
    private static final String MARK_LIST_COMMENTS = "list_comments";
    private static final String MARK_LIST_SLOT = "list_slot";
    private static final String MARK_LIST_APPOINTMENT = "list_appointment";


    private static final String JSP_MANAGE_APPOINTMENTDESKS = "jsp/admin/plugins/appointment/modules/desk/ManageAppointmentDesks.jsp";

    // Properties

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "module.appointment.desk.model.entity.appointmentdesk.attribute.";

    // Views
    private static final String VIEW_MANAGE_APPOINTMENTDESKS = "manageAppointmentDesks";

    // Actions
    private static final String ACTION_CLOSE_APPOINTMENTDESK = "closeAppointmentDesk";
    private static final String ACTION_OPEN_APPOINTMENTDESK = "openAppointmentDesk";

    // Infos
    private static final String INFO_APPOINTMENTDESK_CREATED = "module.appointment.desk.info.appointmentdesk.created";
    private static final String INFO_APPOINTMENTDESK_REMOVED = "module.appointment.desk.info.appointmentdesk.removed";

    // Session variable to store working values
    private AppointmentDesk _appointmentdesk;

    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_APPOINTMENTDESKS, defaultView = true )
    public String getManageAppointmentDesks( HttpServletRequest request )
    {
        int nIdForm = Integer.parseInt( request.getParameter( PARAMETER_ID_FORM ) );
        String strDayDate = request.getParameter( PARAMETER_DATE_DAY );
        LocalDate dateDay = null;

        HashMap<LocalDate, WeekDefinition> mapWeekDefinition = WeekDefinitionService.findAllWeekDefinition( nIdForm );
        if ( StringUtils.isNotEmpty( strDayDate ) )
        {

            Date dateofDay = DateUtil.formatDate( strDayDate, getLocale( ) );
            dateDay = dateofDay.toInstant( ).atZone( ZoneId.systemDefault( ) ).toLocalDate( );

        }
        else
        {

            dateDay = LocalDate.now( );
            strDayDate = DateUtil.getDateString( Date.from( dateDay.atStartOfDay( ).atZone( ZoneId.systemDefault( ) ).toInstant( ) ), getLocale( ) );
        }

        List<Slot> listSlot = SlotService.buildListSlot( nIdForm, mapWeekDefinition, dateDay, dateDay );
        Slot slot = listSlot.stream( ).max( Comparator.comparing( Slot::getMaxCapacity ) ).orElseThrow( NoSuchElementException::new );
        int appointmentDesk = slot.getMaxCapacity( );

        _appointmentdesk = new AppointmentDesk( );
        List<AppointmentDesk> listAppointmentDesks = AppointmentDeskHome.getAppointmentDesksList( );
        List<Place> listPlace = AppointmentDeskService.builListdPlace( listSlot, nIdForm, dateDay, appointmentDesk );
        Map<String, Object> model = getPaginatedListModel( request, MARK_APPOINTMENTDESK_LIST, listAppointmentDesks, JSP_MANAGE_APPOINTMENTDESKS );
        java.sql.Date dateSqlDaey= java.sql.Date.valueOf(dateDay);
        List<Comment> listComment= CommentService.finListComments( dateSqlDaey, dateSqlDaey , nIdForm);
        
        AppointmentFilterDTO filter= new AppointmentFilterDTO ();
        filter.setIdForm(nIdForm);
        filter.setStartingDateOfSearch( java.sql.Date.valueOf( dateDay ) );
        filter.setEndingDateOfSearch( java.sql.Date.valueOf( dateDay ) );
        
        
		List<Appointment> listAppt = AppointmentService.findListAppointmentsByFilter(filter);
        
        model.put( MARK_LIST_COMMENTS, listComment);
        model.put( PARAMETER_NUMB_DESK, appointmentDesk );
        model.put( MARK_LIST_SLOT, listSlot);
        model.put( MARK_LIST_APPOINTMENT, listAppt);

        model.put( MARK_LOCALE, getLocale( ) );
        model.put( PARAMETER_LIST_PLACE, listPlace );
        model.put( MARK_DATE_DAY, strDayDate );
        model.put( MARK_ID_FORM, nIdForm );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_APPOINTMENTDESKS, TEMPLATE_MANAGE_APPOINTMENTDESKS, model );
    }

    /**
     * Process the data capture form of a new appointmentdesk
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CLOSE_APPOINTMENTDESK )
    public String docloseAppointmentDesk( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _appointmentdesk, request, getLocale( ) );
        String strDayDate = request.getParameter( PARAMETER_DATE_DAY );

        // Check constraints
        if ( !validateBean( _appointmentdesk, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_MANAGE_APPOINTMENTDESKS );
        }

        AppointmentDeskService.closeAppointmentDesk(_appointmentdesk, getSlot( request) );
        addInfo( INFO_APPOINTMENTDESK_CREATED, getLocale( ) );
        UrlItem url = new UrlItem( AppPathService.getBaseUrl( request ) + JSP_MANAGE_APPOINTMENTDESKS );
        url.addParameter( PARAMETER_DATE_DAY, strDayDate );
        url.addParameter( PARAMETER_ID_FORM, _appointmentdesk.getIdForm( ) );

        return redirect( request, url.getUrl( ) );
    }

    /**
     * Process the change form of a appointmentdesk
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_OPEN_APPOINTMENTDESK )
    public String doOpenAppointmentDesk( HttpServletRequest request ) throws AccessDeniedException
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_APPOINTMENTDESK ) );
        String strDayDate = request.getParameter( PARAMETER_DATE_DAY );
        String strIdForm = request.getParameter( PARAMETER_ID_FORM );

        AppointmentDeskService.openAppointmentDesk(nId, getSlot( request ));
        addInfo( INFO_APPOINTMENTDESK_REMOVED, getLocale( ) );
        UrlItem url = new UrlItem( AppPathService.getBaseUrl( request ) + JSP_MANAGE_APPOINTMENTDESKS );

        url.addParameter( PARAMETER_DATE_DAY, strDayDate );
        url.addParameter( PARAMETER_ID_FORM, strIdForm );

        return redirect( request, url.getUrl( ) );
    }

    /**
     * Populate a bean AppointmentDesk
     * 
     * @param bean
     *            the bean
     * @param request
     *            the request
     * @param locale
     *            the local
     */
    private void populate( AppointmentDesk appointmentDesk, HttpServletRequest request, Locale locale )
    {

        String strDayDate = request.getParameter( PARAMETER_DATE_DAY );
        String strIdForm = request.getParameter( PARAMETER_ID_FORM );
        String strClosingTime = request.getParameter( PARAMETER_CLOSING_TIME );
        String strOpeningTime = request.getParameter( PARAMETER_OPENING_TIME );
        String strDeskNumber = request.getParameter( PARAMETER_NUMB_DESK );

        Date dateofDay = DateUtil.formatDate( strDayDate, locale );
        LocalDate dateDay = dateofDay.toInstant( ).atZone( ZoneId.systemDefault( ) ).toLocalDate( );

        appointmentDesk.setDay( dateDay );
        appointmentDesk.setIdForm( Integer.parseInt( strIdForm ) );
        appointmentDesk.setEndClosing( LocalTime.parse( strClosingTime ) );
        appointmentDesk.setStartClosing( LocalTime.parse( strOpeningTime ) );
        appointmentDesk.setDeskNumber( Integer.parseInt( strDeskNumber ) );

    }
    /**
     * Get and build the slot 
     * @param request the request
     * @return the slot builded
     */
    private Slot getSlot(HttpServletRequest request) {
    	
	   	 int nIdSlot = Integer.parseInt( request.getParameter( PARAMETER_ID_SLOT ) );
	     int nIdForm = Integer.parseInt( request.getParameter( PARAMETER_ID_FORM ));
	     Slot slot= null;

	        // If nIdSlot == 0, the slot has not been created yet
	        if ( nIdSlot == 0 )
	        {
	            // Need to get all the informations to create the slot
	            LocalDateTime startingDateTime = LocalDateTime.parse( request.getParameter( PARAMETER_STARTING_DATE_TIME ) );
	            LocalDateTime endingDateTime = LocalDateTime.parse( request.getParameter( PARAMETER_ENDING_DATE_TIME ) );
	            boolean bIsOpen = Boolean.parseBoolean( request.getParameter( PARAMETER_IS_OPEN ) );
	            boolean bIsSpecific = Boolean.parseBoolean( request.getParameter( PARAMETER_IS_SPECIFIC ) );
	            int nMaxCapacity = Integer.parseInt( request.getParameter( PARAMETER_MAX_CAPACITY ) );
	            slot = SlotService.buildSlot( nIdForm, new Period( startingDateTime, endingDateTime ), nMaxCapacity, nMaxCapacity, nMaxCapacity, 0, bIsOpen,
	                    bIsSpecific );
	        }
	        else
	        {
	            slot = SlotService.findSlotById( nIdSlot );
	        }
	        return slot;
   }

}
