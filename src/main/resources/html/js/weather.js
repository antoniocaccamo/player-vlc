function refresh_weather( latlng ) {
    
    $.getJSON("http://www.arttechonline.com/weather.php?callback=?&latlng=" + latlng ,
    //$.getJSON("http://free.worldweatheronline.com/feed/weather.ashx?callback=?format=json&num_of_days=0&key=f71e33c435031824120611&includeLocation=yes&q=" + latlng ,
        function(data) {
            // $("#city").text("img/weather/" + data.current.weatherIcon );
            $("#current-icon").attr("src", "img/weather/" + data.current.weatherIcon );            
            $("#current_value").text( data.current.tempC );
            if ( data.forecast ) {
                today = data.forecast[0];
                $("#today_min_value").text( today.lowC );
                $("#today_max_value").text( today.highC );
            }
        }
    );
}


    

