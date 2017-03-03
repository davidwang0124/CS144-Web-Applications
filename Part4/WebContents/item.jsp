<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Item</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" /> 
	<style type="text/css"> 
	  html { height: 100% } 
	  body { height: 100%; margin: 0px; padding: 0px } 
	  #map_canvas { height: 100% } 
	</style> 
	<script type="text/javascript" 
	    src="http://maps.google.com/maps/api/js?sensor=false"> 
	</script> 
	<script type="text/javascript">
		var latitude = ${item.getItemLatitude()};
        var longitude = ${item.getItemLongitude()};
		function initialize() { 
			var latlng = new google.maps.LatLng(latitude, longitude); 
			var myOptions = { 
				zoom: 14, // default is 8  
				center: latlng, 
				mapTypeId: google.maps.MapTypeId.ROADMAP 
			}; 
			var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions); 
			var marker = new google.maps.Marker({
				map: map,
				position: latlng
			});
		} 
	</script>
</head>
<body onload="initialize()"> 
	<div id="map_canvas" style="width:30%; height:30%"></div> 
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