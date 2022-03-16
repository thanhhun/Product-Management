<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>

<style>
.btnDelete, .btnEdit {
	font-size: 15px;
}

.btnDelete {
	color: red;
}

.btnEdit {
	color: #1761fd;
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
				<!--PageListHolder  -->
				<%-- <jsp:useBean id="pagedListHolder" scope="request"
					type="org.springframework.beans.support.PagedListHolder" />
				<c:url value="user/list/${id_NV}.htm" var="pagedLink">
					<c:param name="p" value="~" />
				</c:url> --%>


				<div class="card-header">
					<h4 class="card-title">Danh sách phiếu xuất</h4>
				</div>
				<!--end card-header-->
				<div class="card-body">

					<!-- id="datatable" -->
					<table id="datatable"
						class="table table-bordered dt-responsive nowrap table-striped table-hover"
						style="border-collapse: collapse; border-spacing: 0; width: 100%;">
						<thead>
							<tr>
								<th>Mã PX</th>
								<th>Ngày</th>
								<th>Họ tên khách hàng</th>
								<th>Nhân viên thực hiện</th>
								<th>Mã kho</th>
								<th>Chi tiết</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="pd" items="${phieuXuats}">
								<tr>
									<td>${pd.maPX}</td>
									<td>${pd.ngay}</td>
									<td>${pd.hoTenKH}</td>
									<td>${pd.nhanVien.ten}</td>
									<td>${pd.kho.maKho}</td>
									<td><a
										href="product/phieuxuat/chitiet/${pd.maPX.trim()}/${id_NV}.htm">Chi
											tiết</a></td>
									<td><a class="btnEdit"
										href="product/phieuxuat/${pd.maPX.trim()}/${id_NV}.htm?lnkEdit"><i
											class="ti-settings"></i></a></td>
									<td><a class="btnDelete"
										href="product/phieuxuat/${pd.maPX.trim()}/${id_NV}.htm?lnkDel"><i
											class="ti-trash"></i></a></td>
								</tr>

							</c:forEach>

						</tbody>
					</table>

					<%-- <tg:paging pagedListHolder="${pagedListHolder}"
						pagedLink="${pagedLink}" /> --%>
				</div>
			</div>

			<!-- Check xác nhận xóa -->
			<%-- 	<c:choose>
				<c:when test="${showxacnhan == 'xacnhan'}">
					<form:form>
						<div>Bạn có muốn xóa phiếu xuất này</div>
						<button formaction="product/phieuxuat/action/${id_NV}.htm"
							name="${btnAction}" type="submit"
							class="btn btn-sm btn-outline-primary">Xác nhận</button>
						<button formaction="product/phieuxuat/${id_NV}.htm" type="submit"
							class="btn btn-sm btn-outline-danger">Hủy bỏ</button>
					</form:form>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose> --%>


			<!-- Phieu Xuat -->
			<c:if test="${clickaction == 'showtablekho'}">
				<div class="row">
					<div class="col-lg-3">
						<div class="card">
							<div class="card-header">
								<div class="row align-items-center">
									<div class="col">
										<h4 class="card-title">Lập phiếu xuất</h4>
									</div>
									<!--end col-->
								</div>
								<!--end row-->
							</div>
							<!--end card-header-->
							<div class="card-body">
								<!-- Thông báo -->
								<c:choose>
									<c:when test="${check==null}">
									</c:when>
									<c:when test="${check == 1}">
										<div class="alert alert-success border-0" role="alert">
											<strong>Thông báo!</strong> ${message}.
										</div>
									</c:when>
									<c:otherwise>
										<div class="alert alert-danger border-0" role="alert">
											<strong>Thông báo!</strong> ${message}.
										</div>
									</c:otherwise>
								</c:choose>

								<form:form action="product/phieuxuat/action/${id_NV}.htm"
									modelAttribute="lapphieuxuat">
									<div class="form-group row">
										<label
											class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
											NV</label>
										<div class="col-lg-9 col-xl-8">
											<form:input path="nhanVien.maNV" class="form-control"
												type="text" value="${id_NV}" readonly="true" />
										</div>
									</div>
									<div class="form-group row">
										<label
											class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
											PX</label>
										<div class="col-lg-9 col-xl-8">
											<form:input path="maPX" class="form-control" type="text" />
										</div>
									</div>
									<div class="form-group row">
										<label
											class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Tên
											KH</label>
										<div class="col-lg-9 col-xl-8">
											<form:input path="hoTenKH" class="form-control" type="text" />
										</div>
									</div>
									<div class="form-group row">
										<label
											class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Ngày
											lập</label>
										<div class="col-lg-9 col-xl-8">
											<form:input path="ngay" class="form-control" type="text"
												placeholder="MM/DD/YYYY" />
										</div>
									</div>
									<div class="form-group row">
										<label
											class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
											kho</label>
										<div class="col-lg-9 col-xl-8">
											<form:select path="kho.maKho" class="form-select"
												items="${KhoByMaCN}" itemLabel="maKho" itemValue="maKho" />
										</div>
									</div>
									<div class="form-group row">
										<div class="col-lg-9 col-xl-8 offset-lg-3">
											<form:button name="${btnAction}" type="submit"
												class="btn btn-sm btn-outline-primary">Xác nhận</form:button>
											<form:button formaction="user/index/${id_NV}.htm"
												type="submit" class="btn btn-sm btn-outline-danger">Hủy bỏ</form:button>
										</div>
									</div>
								</form:form>
							</div>
							<!--end card-body-->
						</div>
						<!--end card-->
					</div>
					<!--end col-->
					<div class="col-lg-9">


						<div class="card">
							<!--PageListHolder  -->
							<%-- <jsp:useBean id="pagedListHolder" scope="request"
					type="org.springframework.beans.support.PagedListHolder" />
				<c:url value="user/list/${id_NV}.htm" var="pagedLink">
					<c:param name="p" value="~" />
				</c:url> --%>


							<div class="card-header">
								<h4 class="card-title">Danh sách kho theo Chi Nhánh</h4>
								<p class="text-muted mb-0">
									DataTables has most features enabled by default, so all you
									need to do to use it with your own tables is to call the
									construction function:
									<!-- <code>$().DataTable();</code> -->
									.
								</p>
							</div>
							<!--end card-header-->
							<div class="card-body">
								<!-- id="datatable" -->
								<table id="datatable"
									class="table table-bordered dt-responsive nowrap table-striped table-hover"
									style="border-collapse: collapse; border-spacing: 0; width: 100%;">
									<thead>
										<tr>
											<th>Mã Kho</th>
											<th>Tên Kho</th>
											<th>Địa chỉ</th>
											<th>Mã CN</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="pd" items="${KhoByMaCN}">
											<tr>
												<td>${pd.maKho}</td>
												<td>${pd.tenKho}</td>
												<td>${pd.diaChi}</td>
												<td>${pd.chiNhanh.maCN}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<%-- <tg:paging pagedListHolder="${pagedListHolder}"
						pagedLink="${pagedLink}" /> --%>
							</div>
						</div>

					</div>
					<!--end col-->
				</div>
			</c:if>


			<!-- Chi Tiet Phieu Nhap -->
			<c:if test="${clickaction =='showCTPX'}">

				<div class="card">
					<!--PageListHolder  -->
					<%-- <jsp:useBean id="pagedListHolder" scope="request"
					type="org.springframework.beans.support.PagedListHolder" />
				<c:url value="user/list/${id_NV}.htm" var="pagedLink">
					<c:param name="p" value="~" />
				</c:url> --%>


					<div class="card-header">
						<h4 class="card-title">Danh sách vật tư</h4>
					</div>
					<!--end card-header-->
					<div class="card-body">
						<!-- id="datatable" -->
						<div class="table-responsive" style="max-height: 200px">
							<table id="datatable" class="table table-bordered table-hover">
								<thead>
									<tr>
										<th>Tên vật tư</th>
										<th>Mã vật tư</th>
										<th>Đơn vị tính</th>
										<th>Số lượng tồn</th>
										<th>Avai.Color</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="v" items="${VatTus}">
										<tr>
											<td><img src="resources/img-product/${v.phoTo}" alt=""
												height="40">
												<p class="d-inline-block align-middle mb-0">
													<a href=""
														class="d-inline-block align-middle mb-0 product-name">${v.tenVT}</a>
													<br> <span class="text-muted font-13">size-08
														(Model 2020)</span>
												</p></td>
											<td>${v.maVT}</td>
											<td>${v.donVi}</td>
											<td>${v.soLuongTon}</td>
											<td>
												<ul class="list-inline mb-0">
													<li class="list-inline-item align-middle"><i
														class="fas fa-circle text-success"></i></li>
													<li class="list-inline-item align-middle"><i
														class="fas fa-circle text-pink"></i></li>
													<li class="list-inline-item align-middle"><i
														class="fas fa-circle text-info"></i></li>
													<li class="list-inline-item align-middle"><i
														class="fas fa-circle text-warning"></i></li>
												</ul>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<%-- <tg:paging pagedListHolder="${pagedListHolder}"
						pagedLink="${pagedLink}" /> --%>
					</div>
				</div>

				<div class="row">
					<div class="col-lg-3">
						<div class="card">
							<div class="card-header">
								<div class="row align-items-center">
									<div class="col">
										<h4 class="card-title">Chi tiết phiếu xuất</h4>
									</div>
									<!--end col-->
								</div>
								<!--end row-->
							</div>
							<!--end card-header-->
							<div class="card-body">
								<!-- Thông báo -->

								<form:form
									action="product/phieuxuat/action/chitiet/${id_NV}.htm"
									modelAttribute="chitietphieuxuat">
									<c:choose>
										<c:when test="${check==null}">
										</c:when>
										<c:when test="${check == 1}">
											<div class="alert alert-success border-0" role="alert">
												<strong>Thông báo!</strong> ${message}.
											</div>
										</c:when>
										<c:otherwise>
											<div class="alert alert-danger border-0" role="alert">
												<strong>Thông báo!</strong> ${message}.
											</div>
										</c:otherwise>
									</c:choose>
									<form:input path="id" type="hidden" />
									<div class="form-group row">
										<label
											class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
											PX</label>
										<div class="col-lg-9 col-xl-8">
											<form:input path="phieuXuat.maPX" class="form-control"
												type="text" value="${maPX}" readonly="true" />
										</div>
									</div>
									<div class="form-group row">
										<label
											class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Vật
											tư</label>
										<div class="col-lg-9 col-xl-8">
											<form:select path="vatTu.maVT" class="form-select"
												items="${VatTus}" itemLabel="tenVT" itemValue="maVT" />
										</div>
									</div>
									<div class="form-group row">
										<label
											class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Số
											lượng</label>
										<div class="col-lg-9 col-xl-8">
											<form:input path="soLuong" class="form-control" type="text" />
										</div>
									</div>
									<div class="form-group row">
										<label
											class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Đơn
											giá</label>
										<div class="col-lg-9 col-xl-8">
											<form:input path="donGia" class="form-control" type="text" />
										</div>
									</div>
									<div class="form-group row">
										<div class="col-lg-9 col-xl-8 offset-lg-3">
											<button name="${btnAction}" type="submit"
												class="btn btn-sm btn-outline-primary">Xác nhận</button>
											<button formaction="product/phieuxuat/${id_NV}.htm"
												type="submit" class="btn btn-sm btn-outline-danger">Trở
												về</button>
										</div>
									</div>
								</form:form>
							</div>
							<!--end card-body-->
						</div>
						<!--end card-->
					</div>
					<!--end col-->
					<div class="col-lg-9">
						<div class="card">
							<div class="card-header">
								<h4 class="card-title">
									Chi tiết phiếu xuất của mã <strong>${maPX}</strong>
								</h4>
							</div>
							<!--end card-header-->
							<div class="card-body">
								<!-- id="datatable" -->
								<div class="table-responsive" style="max-height: 270px;">
									<table id="datatable" class="table table-bordered table-hover">
										<thead>
											<tr>
												<th>Mã PX</th>
												<th>Tên VT</th>
												<th>Số lượng</th>
												<th>Đơn giá</th>
												<th></th>
												<th></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="c" items="${CTPXs}">
												<tr>
													<td>${c.phieuXuat.maPX}</td>
													<td>${c.vatTu.tenVT}</td>
													<td>${c.soLuong}</td>
													<td>${c.donGia}</td>
													<td><a class="btnEdit"
														href="product/phieuxuat/chitiet/${c.phieuXuat.maPX}/${c.vatTu.maVT}/${id_NV}.htm?lnkEdit"><i
															class="ti-settings"></i></a></td>
													<td><a class="btnDelete"
														href="product/phieuxuat/chitiet/${c.phieuXuat.maPX}/${c.vatTu.maVT}/${id_NV}.htm?lnkDel"><i
															class="ti-trash"></i></a></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<%-- <tg:paging pagedListHolder="${pagedListHolder}"
						pagedLink="${pagedLink}" /> --%>
							</div>
						</div>
					</div>
					<!--end col-->
				</div>
			</c:if>
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
