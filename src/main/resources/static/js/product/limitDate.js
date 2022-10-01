const dateElement = document.getElementById('currentTime');
const date = new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString().slice(0, -8);
//dateElement.value = date;
//date=date.substr(0,20);
dateElement.setAttribute("min", date);