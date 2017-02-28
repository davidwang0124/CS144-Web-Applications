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
               value="${searchText}"
               placeholder="Search by name, category, or description ">
        <ul class="items">
            <c:forEach items="${searchResults}" var="sr">
                <li>
                    <a href="">
                        <span>${sr.getItemId()}</span>
                        <span>${sr.getName()}</span>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </form>
</body>
</html>
