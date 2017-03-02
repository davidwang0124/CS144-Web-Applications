const endpointBase = "suggest?q=";
function update(query) {

}

const inputBox = document.querySelector("input");
inputBox.addEventListener("keyup", update);
inputBox.addEventListener("change", update);
