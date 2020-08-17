
<jsp:useBean id="manageappointmentdeskAppointmentDesk" scope="session" class="fr.paris.lutece.plugins.appointment.modules.desk.web.AppointmentDeskJspBean" />
<% String strContent = manageappointmentdeskAppointmentDesk.processController ( request , response ); %>


<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../../../AdminFooter.jsp" %>