var app = angular.module('InterviewScheduler', ['ngRoute', 'toastr']);

app.config(function ($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl: "pages/admin-login.html"
        })
        .when("/login", {
            templateUrl: "pages/admin-login.html"
        })
        .when("/candidate-login", {
            templateUrl: "pages/login.html"
        })
        .when("/candidate-schedule", {
            templateUrl: "pages/candidate_set_schedule.html"
        })
        .when("/hr-schedule", {
            templateUrl: "pages/hr_set_time_schedule.html"
        })
        .when("/create-interview", {
            templateUrl: "pages/upload_basic_interview_details.html"
        })
        .when("/upload-files", {
            templateUrl: "pages/upload_files.html"
        })
        .when("/generate-interview-schedule", {
            templateUrl: "pages/schedule_interview.html"
        })
        .when("/interviewer-login", {
            templateUrl: "pages/interviewer_login.html"
        })
        .when("/schedule", {
            templateUrl: "pages/schedule.html"
        })
        .when("/history", {
            templateUrl: "pages/admin_history.html"
        });
});

app.controller('MainController', function ($scope, $location, $http, $rootScope, toastr) {

    $scope.loading = false;
    $scope.CheckLogin = function () {
        var candidate_email = document.getElementById("candidate_email").value;
        var candidate_pass = document.getElementById("candidate_pass").value;
        var request = $http({
            method: "POST",
            url: "http://localhost:8080/schedule/candidate-auth",
            headers: {'Content-Type': 'application/json'},
            data: {
                "name": "",
                "email": candidate_email,
                "password": candidate_pass,
                "day": 7,
                "preference": ""
            }
        }).then(function successCallback(response) {
            $rootScope.candidate_email = response.data.response
            $location.path('/candidate-schedule');
        }, function errorCallback(response) {
            // alert("Error");
            toastr.error("Error");
        });
    };

    $scope.InterviewerLogin = function () {
        var interviewer_email = document.getElementById("interviewer_email").value;
        var interviewer_pass = document.getElementById("interviewer_pass").value;
        var request = $http({
            method: "POST",
            url: "http://localhost:8080/schedule/interviewer-auth",
            headers: {'Content-Type': 'application/json'},
            data: {
                "name": "",
                "email": interviewer_email,
                "password": interviewer_pass,
                "day": 0,
                "preference": ""
            }
        }).then(function successCallback(response) {
            $rootScope.interviewer_email = response.data.response
            $location.path('/hr-schedule');
        }, function errorCallback(response) {
            // alert("Error");
            toastr.error("Error");
        });
    };

    $scope.AdminLogin = function () {
            var admin_email = document.getElementById("admin_email").value;
            var admin_pass = document.getElementById("admin_pass").value;
            var request = $http({
                method: "POST",
                url: "http://localhost:8080/schedule/valid-admin",
                headers: {'Content-Type': 'application/json'},
                data: {
                    "name": "",
                    "email": admin_email,
                    "password": admin_pass
                }
            }).then(function successCallback(response) {
                if(response.data.response == true){
//                    console.log(response.data.response);
                    $rootScope.admin_email = admin_email;
                    $scope.urlCandidate = "http://localhost:8080/schedule/upload-candidate/" + $rootScope.admin_email;
                    $scope.urlInterview = "http://localhost:8080/schedule/upload-interview/" + $rootScope.admin_email;
                    $location.path('/history');
                }
            }, function errorCallback(response) {
                // alert("Error");
                toastr.error("Error");
            });
        };

    $scope.setCandidatePreference = function () {
        var preference = [];
        preference[1] = document.getElementById("pref1select").value;
        preference[2] = document.getElementById("pref2select").value;
        preference[3] = document.getElementById("pref3select").value;
        preference[4] = document.getElementById("pref4select").value;
        for(var i = 1; i < 5; i++) {
            for(var j = i + 1; j < 5; j++) {
                if(preference[i] == preference[j]) {
                    toastr.error("No two preferences can be the same, Please change the options");
                    return;
                }
            }
        }
        var preferenceString = preference[1] + preference[2] + preference[3] + preference[4];
        var candidate_email_from_preference = document.getElementById("candidate_email_from_preference").value;
        var preferredDate = document.getElementById("candidate_preferred_date").value;
        var request = $http({
            method: "POST",
            url: "http://localhost:8080/schedule/candidate-preference?email=" + candidate_email_from_preference,
            headers: {'Content-Type': 'application/json'},
            data: {
                "day": preferredDate,
                "preference": preferenceString
            }
        }).then(function successCallback(response) {
            alert("Your Response has been Recorded");
            $location.path('/candidate-login');
        }, function errorCallback(response){
            toastr.error("Error");
        });
    };

    $scope.getInterviewScheduleDate = function () {
        var array = []
        for (var i = 0; i < 5; i++) {
            array.push(i);
        }
        $scope.count = 0;
        $scope.interviewDate = array;
    };

    $scope.scheduleInterviewTime = function () {
        $scope.schedule = []
        for (var i = 0; i < 5; i++) {
            var day = "";
            for (var column = 0; column < 12; column++) {
                try {
                    var x = document.getElementById(i + '-' + column).checked;
                    if (x == true) {
                        day += '1';
                        // $scope.schedule.push(document.getElementById(i + '-' + column).value)
                    } else {
                        day += '0';
                    }
                } catch (error) {
                    console.log('worked');
                }
            }
            $scope.schedule.push(day);
        }
        // alert($scope.schedule);
        var request = $http({
            method: "POST",
            url: "http://localhost:8080/schedule/interview?email=" + $rootScope.interviewer_email,
            headers: {'Content-Type': 'application/json'},
            data: {
                "preferenceDtos": $scope.schedule
            }
        }).then(function successCallback(response) {
            alert("Available time updated");
            $location.path('/interviewer-login');
        }, function errorCallback(response) {
            toastr.error("Error");
        });
    };

    $scope.getAllSchedule = function () {
        var request = $http({
            method: "GET",
            url: "http://localhost:8080/schedule/get-all-schedule?email=" + $rootScope.admin_email,
            headers: {'Content-Type': 'application/json'}
        }).then(function successCallback(response) {
            // $location.path('/candidate-schedule');
            console.log(response.data);
            $scope.scheduleDetails = response.data.response;
        }, function errorCallback(response) {
            toastr.error("Error");
        });
    };

    $scope.getScheduleDetails = function(index){
        var request = $http({
            method: "GET",
            url: "http://localhost:8080/schedule/get-schedule-by-id/?email=" + $rootScope.candidate_email+"&index="+index,
            headers: {'Content-Type': 'application/json'}
        }).then(function successCallback(response) {
            $scope.schedule = response.data.response;
            console.log(response);
        }, function errorCallback(response) {
            toastr.error("Error");
        });
    }

    $scope.scheduleInterview = function () {
        var request = $http({
            method: "GET",
            url: "http://localhost:8080/schedule/interview-scheduling?email=" + $rootScope.candidate_email,
            headers: {'Content-Type': 'application/json'}
        }).then(function successCallback(response) {
            console.log(response);
//            $scope.schedule = response.data.response;
        }, function errorCallback(response) {
            toastr.error("Error");
        });
    };

    $scope.getFinalSchedule = function(index) {
        var request = $http({
            method: "GET",
            url: "http://localhost:8080/schedule/get-output-by-id?email=" + $rootScope.admin_email + "&index=" + index,
            headers: {'Content-Type': 'application/json'}
        }).then(function successCallback(response){
            $scope.finalSchedule = response.data.response;
            console.log(response.data.response);
            $location.path("/generate-interview-schedule");
        }, function errorCallback(response) {
            toastr.error("Error");
        });
    };

    $scope.showScheduleDetails = function(index) {
        var request = $http({
            method: "GET",
            url: "http://localhost:8080/schedule/get-schedule-by-id/?email=" + $rootScope.admin_email+"&index="+index,
            headers: {'Content-Type': 'application/json'}
        }).then(function successCallback(response) {
            if(response.data.response == false){
                alert('Cannot fetch the details.');
            }
            else{
                $scope.schedule = response.data.response;
                console.log(response.data.response);
                $location.path('/schedule');
                $scope.indexOfAlgoInput = index;
             }
        }, function errorCallback(response) {
            toastr.error("Error");
        });
    };

    $scope.createInterview = function(index){
        var startdate = document.getElementById("start_date").value;
        var numberOfDays = document.getElementById("number_of_days").value;
        var interviewDuration = document.getElementById("interviewer_duration").value;
        var request = $http({
            method: "GET",
            url: "http://localhost:8080/schedule/schedule-basic-details/?email=" + $rootScope.admin_email+"&startDate="+startdate+"&numberOfDays="+numberOfDays+"&interviewDuration="+interviewDuration,
            headers: {'Content-Type': 'application/json'}
        }).then(function successCallback(response) {
//            $scope.schedule = response.data;
            $scope.basicInterviewDetails = response.data.response;
            $location.path('/upload-files');
        }, function errorCallback(response) {
            toastr.error("Error");
        });
    };

    $scope.setHistoryToCreate = function(){
        $location.path('/create-interview')
    };

    $scope.sendEmailsBefore = function(){
        $scope.loading = true;
        var request = $http({
            method: "GET",
            url: "http://localhost:8080/schedule/send-emails-before/?email=" + $rootScope.admin_email,
            headers: {'Content-Type': 'application/json'}
        }).then(function successCallback(response){
//            $scope.schedule = response.data;
            $location.path('/history');
            $scope.loading = false;
        }, function errorCallback(response) {
            toastr.error("Error");
        });
    };

    $scope.scheduleById = function(){
        var request = $http({
            method: "GET",
            url: "http://localhost:8080/schedule/interview-scheduling/?email=" + $rootScope.admin_email + "&index="+$scope.indexOfAlgoInput,
            headers: {'Content-Type': 'application/json'}
        }).then(function successCallback(response){
               $scope.getFinalSchedule($scope.indexOfAlgoInput);
              $location.path('/history');
        }, function errorCallback(response) {
            //$location.path('/history');
            toastr.error("Error");
        });
    }
});