<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Item</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="table.css">
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
</head>
<body>
    <div class="item-container">
        <table class="table-fill">
            <thead>
            <tr>
            <th class="text-left">Property</th>
            <th class="text-left">Value</th>
            </tr>
            </thead>
            <tbody class="table-hover">
            <tr>
            <td class="text-left">id</td>
            <td class="text-left"><%=request.getAttribute("id")%></td>
            </tr>
            <tr>
            <td class="text-left">Name</td>
            <td class="text-left"><%=request.getAttribute("name")%></td>
            </tr>
            <tr>
            <td class="text-left">Category</td>
            <td class="text-left">
                <c:forEach items="${categories}" var="category">
                    <span>${category} </span>
                </c:forEach>
            </td>
            </tr>
            <tr>
            <td class="text-left">Currently</td>
            <td class="text-left"><%=request.getAttribute("currently")%></td>
            </tr>
            <tr>
            <td class="text-left">BuyPrice</td>
            <td class="text-left"><%=request.getAttribute("buyPrice")%></td>
            </tr>
            <tr>
            <td class="text-left">FirstBid</td>
            <td class="text-left"><%=request.getAttribute("firstBid")%></td>
            </tr>
            <tr>
            <td class="text-left">NumberofBids</td>
            <td class="text-left"><%=request.getAttribute("numberofBids")%></td>
            </tr>
            <c:forEach items="${bids}" var="bid">
                <td class="text-left">Bid</td>
                <td class="nested-td">
                    <table class="table-fill nested-table">
                    <tbody>
                        <tr>
                        <td class="text-left">BidderID</td>
                        <td class="text-left">${bid.getBidderId()}</td>
                        </tr>
                        <tr>
                        <td class="text-left">Rating</td>
                        <td class="text-left">{bid.getBidderRating()}</td>
                        </tr>
                        <tr>
                        <td class="text-left">Location</td>
                        <td class="text-left">${bid.getBidderLocation()}</td>
                        </tr>
                        <tr>
                        <td class="text-left">Country</td>
                        <td class="text-left">${bid.getBidderCountry()}</td>
                        </tr>
                        <tr>
                        <td class="text-left">Time{</td>
                        <td class="text-left">id.getBidTime()}</td>
                        </tr>
                        <tr>
                        <td class="text-left">Amount</td>
                        <td class="text-left">{bid.getBidAmount()}</td>
                        </tr>
                    </tbody>
                    </table>
                </td>
            </c:forEach>
            <tr>
            <td class="text-left">location</td>
            <td class="text-left"><%=request.getAttribute("location")%></td>
            </tr>
            <tr>
            <td class="text-left">latitude</td>
            <td class="text-left"><%=request.getAttribute("latitude")%></td>
            </tr>
            <tr>
            <td class="text-left">longitude</td>
            <td class="text-left"><%=request.getAttribute("longitude")%></td>
            </tr>
            <tr>
            <td class="text-left">country</td>
            <td class="text-left"><%=request.getAttribute("country")%></td>
            </tr>
            <tr>
            <td class="text-left">started</td>
            <td class="text-left"><%=request.getAttribute("started")%></td>
            </tr>
            <tr>
            <td class="text-left">ends</td>
            <td class="text-left"><%=request.getAttribute("ends")%></td>
            </tr>
            <tr>
            <td class="text-left">sellerId</td>
            <td class="text-left"><%=request.getAttribute("sellerId")%></td>
            </tr>
            <tr>
            <td class="text-left">description</td>
            <td class="text-left"><%=request.getAttribute("description")%></td>
            </tr>
            </tbody>
        </table>
        <div id="map_canvas"
             data-lat="${item.getItemLatitude()}"
             data-lon="${item.getItemLongitude()}">
        </div>
    </div>

    <script type="text/javascript">
        const dom = document.getElementById("map_canvas");
        const latitude = dom.dataset.lat;
        const longitude = dom.dataset.lon;

        if (latitude && longitude) {
            const latlng = new google.maps.LatLng(latitude, longitude);
            const myOptions = {
                zoom: 14, // default is 8
                center: latlng,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            const map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
            const marker = new google.maps.Marker({
                map: map,
                position: latlng
            });
        }
    </script>
</body>
</html>