<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<!-- Style -->
<style>
.title {
	font-size: 25px;
}
</style>
</head>
<body class="">
	<!-- Left Sidenav -->
	<%@ include file="/WEB-INF/views/include/left-sidenav.jsp"%>
	<!-- end left-sidenav-->
	<!-- container -->
	<div class="page-wrapper">
		<!-- Top Bar Start -->
		<%@ include file="/WEB-INF/views/include/top-bar.jsp"%>
		<!-- Top Bar End -->
		<!-- Page Content-->
		<div class="page-content">
			<div class="card">
				<div class="card-header">
					<h4 class="card-title">Thay đổi mật khẩu</h4>
				</div>
				<!--end card-header-->
				<div class="card-body">
					<form:form action="user/changepass/${id_NV}.htm">
						
						
						<c:choose>
							<c:when test="${check==null}">
								<div class="alert alert-light border-0" role="alert">
                                        <strong>Vui lòng!</strong> Điền thông tin cần chỉnh sửa.
                                    </div>
							</c:when>
							<c:when test="${check == 1}">
								<div class="alert alert-success border-0" role="alert">
									<strong>Thông báo!</strong> ${notify}.
								</div>
							</c:when>
							<c:otherwise>
								<div class="alert alert-danger border-0" role="alert">
									<strong>Thông báo!</strong> ${message1}${message2}.
								</div>
							</c:otherwise>
						</c:choose>
						
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mật
								khẩu hiện tại</label>
							<div class="col-lg-9 col-xl-8">
								<input name="currentpw" class="form-control" type="password"
									placeholder="Nhập mật khẩu hiện tại" />
								<!-- <a href="#" class="text-primary font-12">Quên mật khẩu ?</a> -->

							</div>
						</div>
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mật
								khẩu mới</label>
							<div class="col-lg-9 col-xl-8">
								<input name="newpw" class="form-control" type="password"
									placeholder="Nhập mật khẩu mới">

							</div>
						</div>
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Xác
								nhận mật khẩu</label>
							<div class="col-lg-9 col-xl-8">
								<input name="checkpw" class="form-control" type="password"
									placeholder="Xác nhận lại mật khẩu mới">
								<span class="form-text text-muted font-12">Never share
									your password.</span>
							</div>
						</div>
						<div class="form-group row">
							<div class="col-lg-9 col-xl-8 offset-lg-3">
								<button type="submit" class="btn btn-sm btn-outline-primary">Thay
									đổi</button>
								<button formaction="user/index/${id_NV}.htm" type="submit"
									class="btn btn-sm btn-outline-danger">Hủy</button>
							</div>
						</div>
					</form:form>
				</div>
				<!--end card-body-->

			</div>
			<!--end card-->



			<!-- container -->
			<footer class="footer text-center text-sm-start">
				©
				<script>
					document.write(new Date().getFullYear())
				</script>
				2021 Dastone <span
					class="text-muted d-none d-sm-inline-block float-end">Crafted
					with <i class="mdi mdi-heart text-danger"></i> by Mannatthemes
				</span>
			</footer>
			<!--end footer-->
		</div>
		<!-- end page content -->
	</div>
	<!-- end page-wrapper -->

	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
