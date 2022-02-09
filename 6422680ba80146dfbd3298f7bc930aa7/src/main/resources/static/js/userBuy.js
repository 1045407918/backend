$(document).ready(function () {
    getMovieList();
    function getMovieList() {
        getRequest(
            '/ticket/get/' +sessionStorage.getItem('id'),
            function (res) {
                console.log(res.content);
                renderTicketList(res.content);
            },
            function (error) {
                alert(error);
            });
    }

    // TODO:填空
    function renderTicketList(ticketInfo) {
        var ticketStr="";
        for(let item of ticketInfo){
            var temp= " <tr>" +
                "<td>" +
                "<div>"+item.movieName+"</div>" +
                "</td>" +
                "<td>" +
                "<div>"+item.hallName+"</div>" +
                "</td>" +
                "<td>" +
                     "<div>"+ (item.rowIndex + 1) + "排" + (item.columnIndex + 1) + "座</div>" +
                "</td>" +
                "<td>" +
                      "<div>"+item.startTime.substring(5, 7) + "月" + item.startTime.substring(8, 10) + "日 " + item.startTime.substring(11, 16)+"</div>"+
                "</td>" +
                "<td>" +
                     "<div>"+item.endTime.substring(5, 7) + "月" + item.endTime.substring(8, 10) + "日 " + item.endTime.substring(11, 16)+"</div>"+
                "</td>" +
                "<td>" +
                     "<div>"+item.state+"</div>"+
                "</td>" +
                "</tr>";
            ticketStr=ticketStr+temp;
        }
        $('#userOrder-to-render').html(ticketStr);

    }

});