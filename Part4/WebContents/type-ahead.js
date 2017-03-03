const endpointBase = "suggest?q=";
const suggestions = document.querySelector(".suggestions");

// Send request to proxyServlet every time we need to update.
function update() {
    const query = this.value;
    let request = new XMLHttpRequest();
    if (!request) return false;
    request.onreadystatechange = function() {
        if (request.readyState === XMLHttpRequest.DONE && request.status === 200) {
            // now we get the data
            const xml = request.responseXML;

            const suggestionDataArray = xml ? xml.getElementsByTagName("suggestion") : [];
            suggestions.innerHTML = Array.from(suggestionDataArray)
                .map(suggNode => suggNode.getAttribute("data"))
                .map(suggStr => {
                    return `<li>${suggStr}</li>`;
                })
                .join("");
        } else {
            console.log(request.status);
        }
    };
    request.open("GET", endpointBase + query);
    request.send();
}

const inputBox = document.querySelector("input");
inputBox.addEventListener("keyup", update);
inputBox.addEventListener("change", update);
document.querySelector(".suggestions").addEventListener("click", (e) => {
    console.log(e.target.textContent);
    inputBox.value = e.target.textContent;
});
