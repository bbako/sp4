<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

	<div class="container">
		<h2>Modal Example</h2>
		<!-- Trigger the modal with a button -->
		<button type="button" class="btn btn-info btn-lg" id="modalBtn">Open
			Modal</button>

		<!-- Modal -->
		<div class="modal fade" id="myModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Modal Header</h4>
					</div>

					<div class="modal-body">
						<style>
							.fileDrop {
								width: 400px;
								height: 300px;
								background-color: gray;
							}
							</style>
						<div class="fileDrop"></div>
						<!-- <form id="f1" action="upload2" method="post" enctype="multiparts/form-data">
							<p>
								Some text in the modal. <input type="file" name="file[]">
							</p>
							<p>
								Some text in the modal. <input type="file" name="fule[]">
							</p>
							<p>
								Some text in the modal. <input type="file" name="file[]">
							</p>
						</form> -->
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger saveBtn">Save</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>					
				</div>

			</div>
		</div>
		<!-- end modal -->
	<ul class="uploadedList">
	</ul>
	</div>
	<script>
		$(document).ready(function() {
			$("#modalBtn").click(function() {
				$("#myModal").modal("toggle");
			});

			$(".saveBtn").click(function(e) {
				$("#f1").submit();
			});

			$(".fileDrop").on("dragenter dragover",
					function(event) {
						event.preventDefault();

					});

			$(".fileDrop").on("drop",	function(event) {
				event.preventDefault();

				var files = event.originalEvent.dataTransfer.files;

				console.log(files);
				var formData = new FormData();

				for (var i = 0; i < files.length; i++) {
					formData.append("file[]",
							files[i]);
				}
				console.log(formData);
				$.ajax({
					url : "/web/upload2",
					data : formData,
					processData : false,
					contentType : false,
					type : 'post',
					success : function(result) {											
						for(var i=0;i<result.length;i++){
							var str = "<li><img src='display?fileName="+result[i]+"'><button>삭제</button></li>"								
							$(".uploadedList").append(str);
							
							$(".uploadedList li button").on("click",function(e){
								var $target = $(e.target);
								$target.parent().remove();
								$.ajax({
									type:'delete',
									url:'/web/delete',
									data:result[i],
									success:function(re){
										console.log(re);
									}
								});
							});							
						}						
					}
				});
				$("#myModal").modal("hide");
			});
		});
	</script>
</body>
</html>