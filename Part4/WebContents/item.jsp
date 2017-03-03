<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Item</title>
</head>
<body>
	<p>id: <%=request.getAttribute("id")%></p>
	<p>Name: <%=request.getAttribute("name")%></p>
	<p>
		<c:forEach items="${categories}" var="category">
            <li>
                <span>category: ${category}</span>
            </li>
        </c:forEach>
	</p>
    <p>currently: <%=request.getAttribute("currently")%></p>
    <p>buyPrice: <%=request.getAttribute("buyPrice")%></p>
    <p>firstBid: <%=request.getAttribute("firstBid")%></p>
    <p>numberofBids: <%=request.getAttribute("numberofBids")%></p>
    <p>
		<c:forEach items="${bids}" var="bid">
            <li>
                <span>BidderID: ${bid.getBidderId()}, </span>
                <span>rating: ${bid.getBidderRating()}, </span>
                <span>location: ${bid.getBidderLocation()}, </span>
                <span>country: ${bid.getBidderCountry()}, </span>
				<span>time: ${bid.getBidTime()}, </span>
                <span>amount: ${bid.getBidAmount()}</span>
            </li>
        </c:forEach>
	</p>
    <p>location: <%=request.getAttribute("location")%></p>
    <p>latitude: <%=request.getAttribute("latitude")%></p>
    <p>longitude: <%=request.getAttribute("longitude")%></p>
    <p>country: <%=request.getAttribute("country")%></p>
    <p>started: <%=request.getAttribute("started")%></p>
    <p>ends: <%=request.getAttribute("ends")%></p>
    <p>sellerId: <%=request.getAttribute("sellerId")%></p>
    <p>description: <%=request.getAttribute("description")%></p>
</body>
</html>