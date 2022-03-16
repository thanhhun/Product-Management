<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="topbar">
	<!-- Navbar -->
	<nav class="navbar-custom">
		<ul class="list-unstyled topbar-nav float-end mb-0">
			<li class="dropdown hide-phone"><a
				class="nav-link dropdown-toggle arrow-none waves-light waves-effect"
				data-bs-toggle="dropdown" href="#" role="button"
				aria-haspopup="false" aria-expanded="false"> <i
					data-feather="search" class="topbar-icon"></i>
			</a>
				<div class="dropdown-menu dropdown-menu-end dropdown-lg p-0">
					<!-- Top Search Bar -->
					<div class="app-search-topbar">
						<form action="#" method="get">
							<input type="search" name="search"
								class="from-control top-search mb-0" placeholder="Type text...">
							<button type="submit">
								<i class="ti-search"></i>
							</button>
						</form>
					</div>
				</div></li>
			<li class="dropdown"><a
				class="nav-link dropdown-toggle waves-effect waves-light nav-user"
				data-bs-toggle="dropdown" href="#" role="button"
				aria-haspopup="false" aria-expanded="false"> <span
					class="ms-1 nav-user-name hidden-sm">${hoTen}</span> <img
					src="resources/dashtone/assets/images/users/user-5.jpg"
					alt="profile-user" class="rounded-circle thumb-xs">
			</a>
				<div class="dropdown-menu dropdown-menu-end">
					<a class="dropdown-item" href="user/index/${id_NV}.htm"><i
						data-feather="user"
						class="align-self-center icon-xs icon-dual me-1"></i> Profile</a>
					<div class="dropdown-divider mb-0"></div>
					<a class="dropdown-item" href="login/exit.htm"><i
						data-feather="power"
						class="align-self-center icon-xs icon-dual me-1"></i> Logout</a>
				</div></li>
		</ul>
		<!--end topbar-nav-->

		<ul class="list-unstyled topbar-nav mb-0">
			<li>
				<button class="nav-link button-menu-mobile">
					<i data-feather="menu" class="align-self-center topbar-icon"></i>
				</button>
			</li>
			<li class="creat-btn">
				<div class="nav-link">
					<a class=" btn btn-sm btn-soft-primary" href="#" role="button"><i
						class="fas fa-plus me-2"></i>New Task</a>
				</div>
			</li>
		</ul>
	</nav>
	<!-- end navbar-->
</div>
