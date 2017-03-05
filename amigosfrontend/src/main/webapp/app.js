var app = angular.module('myApp',['ngRoute']);

app.config(function($routeProvider){
	
	$routeProvider
	
	.when('/',{
		templateUrl : 'home.html',
		controller : 'indexController'
	})
	.when('/home',{
		templateUrl : 'home.html',
		controller : 'indexController'
	})
	.when('/login',{
	
		templateUrl : 'c_user/login.html',
		controller  : 'UserController'
		
		
	})
	.when('/logout',{
	
		templateUrl : '',
		controller  : ''
		
	})
	.when('/chat',{
	
		templateUrl : 'chat/chat.html',
		controller  : 'ChatController'
		
	})
	
	.when('/chat/:secondUser',{
	
		templateUrl : 'chat/chat.html',
		controller  : 'ChatController'
		
	})
	
	.when('/groupchat',{
	
		templateUrl : 'groupchat/groupchat.html',
		controller  : 'GroupChatController'
		
	})
	
	
	.when('/aboutus',{
		templateUrl : 'template/aboutus.html'
	})
	.when('/friends',{
		templateUrl : 'friends/friend.html',
		controller  :  'FriendController'
	})
	.when('/friends/:secondUser',{
		templateUrl : 'friends/friend.html',
		controller  :  'FriendController'
	})
	.when('/searchUser',{
		templateUrl : 'c_user/searchUser.html',
		controller  :  'UserController'
	})
	.when('/jobs',{
		templateUrl : 'c_job/job.html',
		controller : 'JobController'
	})
	.when('/blogs',{
		templateUrl : 'blogs/blog.html',
		controller: 'BlogController'
	})
	.when('/blogs/:secondUser',{
		templateUrl : 'blogs/blog.html',
		controller: 'BlogController'
	})
	.when('/forums',{
		templateUrl : 'forums/forums.html',
		controller: 'ForumController'
	})
	.when('/forums/:secondUser',{
		templateUrl : 'forums/forums.html',
		controller: 'ForumController'
	})
	.when('/profile',{
		templateUrl : 'profile/profile.html',
		controller: 'ProfileController'
	})
	.when('/profile/:secondUser',{
		templateUrl : 'profile/profile.html',
		controller: 'ProfileController'
	})
	.when('/createBlog',{
		templateUrl: 'c_blog/createBlog.html',
		controller: 'BlogController'
	})
	.when('/events',{
		templateUrl: 'c_event/listEvents.html',
	    controller: 'EventController'
	})
	
})