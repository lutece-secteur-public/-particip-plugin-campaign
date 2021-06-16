<jsp:useBean id="managecampaignsTheme" scope="session" class="fr.paris.lutece.plugins.campaign.web.ThemeJspBean" />
<% String strContent = managecampaignsTheme.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
