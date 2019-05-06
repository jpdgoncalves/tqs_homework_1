/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * <a class="dropdown-item" href="#">Action</a>
 */
console.log("Loading index.js");
let locations;
let weather_desc;
let location_name;
let min_temp;
let max_temp;

$(document).ready(function () {
    locations = $("#locations");
    weather_desc = $("#weather_desc");
    location_name = $("#location");
    min_temp = $("#min_temp");
    max_temp = $("#max_temp");
    
    makeRequest("api/weather/locations",addDropDownItems);
    makeRequest("api/weather/default",updateWeatherData);
});

function makeRequest(path,callback) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            callback(JSON.parse(this.responseText));
        }
    };
    xhttp.open("GET", path, false);
    xhttp.send();
}

function addDropDownItems(json) {
    for(let location_data of json) {
        let dropdown_item = createDropDownItem(location_data.id, location_data.location);
        locations.append(dropdown_item);
    }
}

function updateWeatherData(json) {
    console.log(json);
    location_name.children().first().text(json.location);
    weather_desc.children().first().text(json.weather);
    min_temp.children().children().text(json.min_temperature);
    max_temp.children().children().text(json.max_temperature);
}

function createDropDownItem(id, name) {
    let a = $(document.createElement("A"));
    a.addClass("dropdown-item");
    a.data("id", id);
    a.text(name);
    a.click(getWeatherDataFor);
    return a;
}

function getWeatherDataFor(e) {
    let a = $(e.target);
    let id = a.data("id");
    makeRequest("api/weather/" + id,updateWeatherData);
}