<%--
  Created by IntelliJ IDEA.
  User: Muffin
  Date: 9/20/16
  Time: 8:10 PM
  To change this template use File | Settings | File Templates.

--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="/WEB-INF/jsp/custom.tld"%>
<%@ taglib prefix="re" uri="/WEB-INF/jsp/resource.tld"%>
<%@ include file="header.jsp" %>


<div id="terrain" class ="container">
    <div class="row">
        <div class="col-md-2 col-md-offset-5 col-sm-offset-4 col-sm-4"><img style="width:100%" src="<c:url value="/resources/images/error.png"/>"/></div>
    </div>
    <div class="row">
        <c:choose>
            <c:when test="${message == null}">
                <div style="background-color: #997744;text-align: center" class="col-md-offset-4 col-md-4"> <p><spring:message code="error.notAvailable"/></p></div>
                <div class="col-md-offset-4 col-md-4" style="text-align: center"><a href="<c:url value="/" />"/><button class="myButton" id="gotoDir"><spring:message code="error.goBack" /></button></a></div>
            </c:when>
            <c:otherwise>
                  <div class="row">
                    <div style="background-color: #997744;text-align: center" class="col-md-offset-4 col-md-4"> <p><c:out value="${message}"/></p>

                  </div>  
                  </div>
                  

                  <div class="row">

                      <div class="col-md-offset-5">
                        <div class="col-md-2" >
                        <button class="myButton" id="gotoDir" onclick="history.go(-1)"><spring:message code="error.goBack" /></button>
                      </div>

                      <div class="col-md-3">
                        <c:choose>
                          <c:when test="${gameOver}">
                            <a href="<c:url value="/restore"/>"><button class="myButton" id="startAgain" onclick=""><spring:message code="game.startAgain" /></button></a>
                          </c:when>
                        </c:choose>  
                      </div>  
                      </div>

                      
                  </div>              
            </c:otherwise>
        </c:choose>
    </div>
        
</div>


<%@ include file="footer.jsp" %>
