<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Ebay Search</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <form class="search-form">
        <div class="search">
            <input type="text" name="q" value="${searchText}" autocomplete="off">
            <div class="button-groups">
                <a class="button" href="search?next=1"><span>↓</span></a>
                <button class="button">↩︎</button>
            </div>
        </div>
        <ul class="suggestions"></ul>
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

    <script src="type-ahead.js"></script>
</body>
</html>
