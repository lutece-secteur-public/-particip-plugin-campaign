<jsp:useBean id="managecampaignsPhase" scope="session" class="fr.paris.lutece.plugins.campaign.web.PhaseJspBean" />
<% String strContent = managecampaignsPhase.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
