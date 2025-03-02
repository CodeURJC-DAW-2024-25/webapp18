window.onload = function () {

  console.log(apartmentNames)
  console.log(reviewsAverage)

  //Better to construct options first and then pass it as a parameter
  var options = {
    title: {
      text: "Average review by apartment"
    },
    data: null
  };

  let apartmentsData = {
    type: "column",
    dataPoints : []
  }

  for (let i = 0; i < apartmentNames.length; i++) {
    apartmentsData.dataPoints.push({label : apartmentNames[i], y : Number(reviewsAverage[i])});
  }

  options.data = apartmentsData;

  $("#chartContainer").CanvasJSChart(options);
}
