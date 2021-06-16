<jsp:useBean id="managecampaignsCampaign" scope="session" class="fr.paris.lutece.plugins.campaign.web.CampaignJspBean" />
<% String strContent = managecampaignsCampaign.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
