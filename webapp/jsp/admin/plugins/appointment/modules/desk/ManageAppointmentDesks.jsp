<jsp:useBean id="comment" scope="session" class="fr.paris.lutece.plugins.appointment.web.CommentJspBean" />
<jsp:useBean id="manageappointmentdeskAppointmentDesk" scope="session" class="fr.paris.lutece.plugins.appointment.modules.desk.web.AppointmentDeskJspBean" />
<% String strContent = manageappointmentdeskAppointmentDesk.processController ( request , response ); %>


<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />

<%= comment.getCommentInfos( ) %>
<%= strContent %>

<%@ include file="../../../../AdminFooter.jsp" %>