<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/view/client/include/css.jsp"></jsp:include>

</head>
<body>
	<div class="wrapper" id="wrapper">
	<jsp:include page="/view/client/include/header.jsp"></jsp:include>
		<!-- Start Search Popup -->
		<!-- End Search Popup -->
       <!-- Start Bradcaump area -->
        <div class="ht__bradcaump__area bg-image--3">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="bradcaump__inner text-center">
                        	<h2 class="bradcaump-title">Giỏ hàng</h2>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- End Bradcaump area -->
         <!-- cart-main-area start -->
        <div class="cart-main-area section-padding--lg bg--white">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 col-sm-12 ol-lg-12">
                        <i style="color:red">${message}</i>
                        <form action="#">               
                            <div class="table-content wnro__table table-responsive">
                                <table id="table">
                                    <thead>
                                        <tr class="title-top">
                                            <th class="product-thumbnail">Hình ảnh</th>
                                            <th class="product-name">Sản phẩm</th>
                                            <th class="product-price">Giá</th>
                                            <th class="product-quantity">Số lượng</th>
                                            <th class="product-subtotal">Tổng tiền</th>
                                            <th class="product-remove">Xóa</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${sessionScope.cart}" var="map">
                                    		<tr>
                                            <td class="product-thumbnail"><a href="#"><img src="/NongSan/downloadProduct?filename=${map.value.product.imageName }" alt="product img"></a></td>
                                            <td class="product-name"><a href="#">${ map.value.product.name }</a></td>
                                            <td class="product-price"><span class="amount" id="amountId">${map.value.unitPrice }</span></td>
                                            <td class="product-quantity"><input type="number" readonly id="valueNumber" value="${map.value.quantity}"></td>
                                            <td class="product-subtotal"><fmt:formatNumber value="${map.value.unitPrice * map.value.quantity}" maxFractionDigits="2" /></td>
                                            <td class="product-remove"><a href="/NongSan/remove_cartitem?key=${map.key }">X</a></td>
                                       		</tr>
                                    	</c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </form> 
                        <div class="cartbox__btn">
                            <ul class="cart__btn__list d-flex flex-wrap flex-md-nowrap flex-lg-nowrap justify-content-between">
                                <li><a href="/NongSan/home">Cập nhật giỏ hàng</a></li>
                                <li><a href="/NongSan/member/pay">Thanh toán</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-6 offset-lg-6">
                        <div class="cartbox__total__area">
                            <div class="cartbox-total d-flex justify-content-between">
                                <ul class="cart__total__list">
                                    <li>Tổng tiền giỏ hàng</li>
                                </ul>
                                <ul class="cart__total__tk">
                                    <li id="totalCart"></li>
                                </ul>
                            </div>
                            <div class="cart__total__amount">
                                <span></span>
                                <span id="totalFinal"></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>  
        </div>
        <!-- cart-main-area end -->
	<script >
		var table =  document.getElementById("table");
		var sumVal =0;
		for(var i=1 ;i<table.rows.length;i++){
			sumVal= sumVal + parseFloat(table.rows[i].cells[4].innerHTML);
		}
		//console.log(sumVal);
		var cartTotal = document.getElementById("totalCart");
		cartTotal.innerHTML=sumVal;
	</script>
	<jsp:include page="/view/client/include/footer.jsp"></jsp:include>
	</div>
	<jsp:include page="/view/client/include/js.jsp"></jsp:include>
</body>
</html>