$(document).ready(function(){

    var movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    var userId = sessionStorage.getItem('id');
    var isLike = false;

    getMovie();
    if(sessionStorage.getItem('role') === 'admin')
        getMovieLikeChart();

    function getMovieLikeChart() {
       getRequest(
           '/movie/' + movieId + '/like/date',
           function(res){
               var data = res.content,
                    dateArray = [],
                    numberArray = [];
               data.forEach(function (item) {
                   dateArray.push(item.likeTime);
                   numberArray.push(item.likeNum);
               });

               var myChart = echarts.init($("#like-date-chart")[0]);

               // 指定图表的配置项和数据
               var option = {
                   title: {
                       text: '想看人数变化表'
                   },
                   xAxis: {
                       type: 'category',
                       data: dateArray
                   },
                   yAxis: {
                       type: 'value'
                   },
                   series: [{
                       data: numberArray,
                       type: 'line'
                   }]
               };

               // 使用刚指定的配置项和数据显示图表。
               myChart.setOption(option);
           },
           function (error) {
               alert(error);
           }
       );
    }

    function getMovie() {
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                var data = res.content;
                isLike = data.islike;
                repaintMovieDetail(data);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function repaintMovieDetail(movie) {
        !isLike ? $('.icon-heart').removeClass('error-text') : $('.icon-heart').addClass('error-text');
        $('#like-btn span').text(isLike ? ' 已想看' : ' 想 看');
        $('#movie-img').attr('src',movie.posterUrl);
        $('#movie-name').text(movie.name);
        $('#order-movie-name').text(movie.name);
        $('#movie-description').text(movie.description);
        $('#movie-startDate').text(new Date(movie.startDate).toLocaleDateString());
        $('#movie-type').text(movie.type);
        $('#movie-country').text(movie.country);
        $('#movie-language').text(movie.language);
        $('#movie-director').text(movie.director);
        $('#movie-starring').text(movie.starring);
        $('#movie-writer').text(movie.screenWriter);
        $('#movie-length').text(movie.length);
        // 默认填充修改表单
        $('#movie-name-input').val(movie.name);
        let date = new Date(movie.startDate);
        let day = ('0' + date.getDate()).slice(-2);
        let month = ('0' + (date.getMonth() + 1)).slice(-2);
        let formatDate = date.getFullYear() + '-' + month + '-' + day;
        $('#movie-date-input').val(formatDate);
        $('#movie-img-input').val(movie.posterUrl);
        $('#movie-description-input').text(movie.description);
        $('#movie-type-input').val(movie.type);
        $('#movie-length-input').val(movie.length);
        $('#movie-country-input').val(movie.country);
        $('#movie-language-input').val(movie.language);
        $('#movie-director-input').val(movie.director);
        $('#movie-star-input').val(movie.starring);
        $('#movie-writer-input').val(movie.screenWriter);

        $('#movieToDel-name').text('《' + movie.name + '》');
    }

    // user界面才有
    $('#like-btn').click(function () {
        var url = isLike ?'/movie/'+ movieId +'/unlike?userId='+ userId :'/movie/'+ movieId +'/like?userId='+ userId;
        postRequest(
             url,
            null,
            function (res) {
                 isLike = !isLike;
                getMovie();
            },
            function (error) {
                alert(error);
            });
    });

    // admin界面才有
    $("#modify-btn").click(function () {
        $('#movieModifyModal').modal('show');
    });
    $("#movie-form-modify-submit").click(function () {
        var formData = getMovieForm();
        formData.id = movieId;
        if(!validateMovieForm(formData)) {
            return;
        }
        postRequest(
            '/movie/update',
            formData,
            function (res) {
                $("#movieModal").modal('hide');
                if(res.success == false){
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
        window.location.reload();
    });

    $("#delete-btn").click(function () {
        $('#movieDeleteModal').modal('show');
    });

    $("#movie-form-delete-submit").click(function () {
        let data = {
            movieIdList : [movieId]
        };
        postRequest(
            '/movie/off/batch',
            data,
            function (res) {
                $('#movieDeleteModal').modal('hide');
                if(res.success == false){
                    alert(res.message);
                }
            },function (err) {
                alert(err);
            });
    });

    function getMovieForm() {
        return {
            name: $('#movie-name-input').val(),
            startDate: $('#movie-date-input').val(),
            posterUrl: $('#movie-img-input').val(),
            description: $('#movie-description-input').val(),
            type: $('#movie-type-input').val(),
            length: $('#movie-length-input').val(),
            country: $('#movie-country-input').val(),
            starring: $('#movie-star-input').val(),
            director: $('#movie-director-input').val(),
            screenWriter: $('#movie-writer-input').val(),
            language: $('#movie-language-input').val()
        };
    }

    function validateMovieForm(data) {
        var isValidate = true;
        if(!data.name) {
            isValidate = false;
            $('#movie-name-input').parent('.form-group').addClass('has-error');
        }
        if(!data.posterUrl) {
            isValidate = false;
            $('#movie-img-input').parent('.form-group').addClass('has-error');
        }
        if(!data.startDate) {
            isValidate = false;
            $('#movie-date-input').parent('.form-group').addClass('has-error');
        }
        return isValidate;
    }

});