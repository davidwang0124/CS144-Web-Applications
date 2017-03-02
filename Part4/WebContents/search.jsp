<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ebay Search</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <form class="search-form">
        <input type="text" class="search"
               name="q" value="${searchText}"
               placeholder="name/category/description">
        <div class="button-groups">
            <a class="button" href="search?next=1">NEXT</a>
            <button class="button">SEARCH</button>
        </div>
        <ul class="items">
            <c:forEach items="${searchResults}" var="sr">
                <li>
                    <a href="item?id=${sr.getItemId()}">
                        <span>${sr.getName()}</span>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </form>
</body>
</html>
