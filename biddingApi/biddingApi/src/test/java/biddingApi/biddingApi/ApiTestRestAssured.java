package biddingApi.biddingApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import biddingApi.biddingApi.Entities.BiddingData;
import biddingApi.biddingApi.ErrorConstants.Constants;
import biddingApi.biddingApi.Model.BidPostRequest;
import biddingApi.biddingApi.Model.BidPostResponse;
import biddingApi.biddingApi.Model.BidPutRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class ApiTestRestAssured {

	private static String id1;
	private static String id2;

	private static long pageNo_truckApproved_False = 0;
	private static long cnt_truckApproved_False = 0;
	private static long pageNo_LoadId = 0;
	private static long cnt_LoadId = 0;
	private static long cnt_TransporterId = 0;
	private static long pageNo_transporterId = 0;
	private static long pageNo_transporterId_truckApproved_False = 0;
	private static long cnt_TransporterId_truckApproved_False = 0;
	private static long pageNo_transporterId_LoadId = 0;
	private static long cnt_TransporterId_LoadId = 0;

	@BeforeAll
	public static void setup() throws Exception {
		RestAssured.baseURI = Constants.BASE_URI;

//		Response response2;
//		pageNo_truckApproved_False = 0;
//		cnt_truckApproved_False = 0;
//		while (true) {
//			response2 = RestAssured.given().param("pageNo", pageNo_truckApproved_False).param("truckApproved", false)
//					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
//					.extract().response();
//
//			cnt_truckApproved_False += response2.jsonPath().getList("$").size();
//			if (response2.jsonPath().getList("$").size() != Constants.pageSize)
//				break;
//
//			pageNo_truckApproved_False++;
//
//		}
//
		Response response3;
		pageNo_LoadId = 0;
		cnt_LoadId = 0;
		while (true) {
			 response3 = RestAssured.given().param("pageNo", pageNo_LoadId).param("loadId", "load:123")
					.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
					.response();

			cnt_LoadId += response3.jsonPath().getList("$").size();
			if (response3.jsonPath().getList("$").size() != Constants.pageSize)
				break;

			pageNo_LoadId++;

		}
//
//		Response response4;
//		pageNo_transporterId = 0;
//		cnt_TransporterId = 0;
//		while (true) {
//			response4 = RestAssured.given().param("pageNo", pageNo_transporterId)
//					.param("transporterId", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69")
//					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
//					.extract().response();
//
//			cnt_TransporterId += response4.jsonPath().getList("$").size();
//			if (response4.jsonPath().getList("$").size() != Constants.pageSize)
//				break;
//
//			pageNo_transporterId++;
//
//		}
//
//		Response response5;
//		pageNo_transporterId_truckApproved_False = 0;
//		cnt_TransporterId_truckApproved_False = 0;
//		while (true) {
//			response5 = RestAssured.given().param("pageNo", pageNo_transporterId_truckApproved_False)
//					.param("transporterId", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69")
//					.param("truckApproved", false).header("accept", "application/json")
//					.header("Content-Type", "application/json").get().then().extract().response();
//
//			cnt_TransporterId_truckApproved_False += response5.jsonPath().getList("$").size();
//			if (response5.jsonPath().getList("$").size() != Constants.pageSize)
//				break;
//
//			pageNo_transporterId_truckApproved_False++;
//
//		}
//
//		Response response6;
//		pageNo_transporterId_LoadId = 0;
//		cnt_TransporterId_LoadId = 0;
//		while (true) {
//			response6 = RestAssured.given().param("pageNo", pageNo_transporterId_LoadId)
//					.param("transporterId", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69")
//					.param("truckApproved", true).header("accept", "application/json")
//					.header("Content-Type", "application/json").get().then().extract().response();
//
//			cnt_TransporterId_LoadId += response6.jsonPath().getList("$").size();
//			if (response6.jsonPath().getList("$").size() != Constants.pageSize)
//				break;
//
//			pageNo_transporterId_LoadId++;
//
//		}

		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:123", "load:123", (long) 20,
				BiddingData.UnitValue.PER_TON, Arrays.asList("truck:123"),null);

		String inputJson = mapToJson(bidPostRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		BidPostRequest bidPostRequest1 = new BidPostRequest("transporterId:123", "load:1234", (long) 20,
				BiddingData.UnitValue.PER_TON, Arrays.asList("truck:123"), null);

		String inputJson1 = mapToJson(bidPostRequest1);

		Response response0 = RestAssured.given().header("", "").body(inputJson1).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(Constants.success, response.jsonPath().getString("status"));
		id1 = response.jsonPath().getString("bidId");

		assertEquals(200, response0.statusCode());
		assertEquals(Constants.success, response0.jsonPath().getString("status"));
		id2 = response0.jsonPath().getString("bidId");

		
		
		
	}

	@Test
	@Order(1)
	public void addDataFailed_addDataFailed_invalidLoadId_null() throws Exception {

		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:123", null, (long) 20,
				BiddingData.UnitValue.PER_TON, Arrays.asList("truck:123"), null);

		String inputJson = mapToJson(bidPostRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(Constants.pLoadIdIsNull, response.jsonPath().getString("status"));

	}

//	@Test
//	@Order(1)
//	public void addDataFailed_sameTruckNo_differentTransporterId() throws Exception {
//
//		TruckRequest bidPostRequest = new TruckRequest("transporter:123", "AA 00 AA 1111", "alpha", 50,
//				"driver:0de885e0-5f43-4c68-8dde-b25464747865", 20, null, TruckData.TruckType.OPEN_HALF_BODY);
//
//		String inputJson = mapToJson(bidPostRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(Constants.ADD_SUCCESS, response.jsonPath().getString("status"));
//		String id = response.jsonPath().getString("truckId");
//
//		Response response1 = RestAssured.given().header("", "").delete("/" + id).then().extract().response();
//
//		assertEquals(200, response1.statusCode());
//		assertEquals(Constants.DELETE_SUCCESS, response1.jsonPath().getString("status"));
//	}

	@Test
	@Order(2)
	public void addDataFailed_LoadIdAndTransporterIdExists() throws Exception {

		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:123",
				"load:123", (long) 23, BiddingData.UnitValue.PER_TON, Arrays.asList("truck:123"), null);
		String inputJson = mapToJson(bidPostRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(Constants.pLoadIdAndTransporterIdExists, response.jsonPath().getString("status"));

	}

	@Test
	@Order(3)
	public void addDataFailed_invalidRate_null() throws Exception {
		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"load:1345", null, BiddingData.UnitValue.PER_TON, Arrays.asList("truck:123"), null);

		String inputJson = mapToJson(bidPostRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(Constants.pTransporterRateIsNull, response.jsonPath().getString("status"));

	}

	@Test
	@Order(4)
	public void addDataFailed_invalidUnitValue_null() throws Exception {
		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"load:1345", (long) 23, null, Arrays.asList("truck:123"), null);
		String inputJson = mapToJson(bidPostRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(Constants.unitValueisNull, response.jsonPath().getString("status"));

	}

//	@Test
//	@Order(5)
//	public void addDataFailed_invalidTruckNo_notNull2() throws Exception {
//
//		TruckRequest bidPostRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
//				"AA 00 AA 111", "alpha", 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865", 20, null,
//				TruckData.TruckType.OPEN_HALF_BODY);
//
//		String inputJson = mapToJson(bidPostRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(Constants.TRUCK_NO_IS_INVALID, response.jsonPath().getString("status"));
//
//	}

	@Test
	@Order(6)
	public void getBiddingDataWithIdSuccess() throws Exception {

		Response response = RestAssured.given().header("", "").header("accept", "application/json")
				.header("Content-Type", "application/json").get("/" + id1).then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals("transporterId:123",response.jsonPath().getString("transporterId"));

		assertEquals("load:123", response.jsonPath().getString("loadId"));
		assertEquals(20,  Long.parseLong(response.jsonPath().getString("rate")));
		assertEquals(String.valueOf(BiddingData.UnitValue.PER_TON), response.jsonPath().getString("unitValue"));
		assertEquals(String.valueOf(Arrays.asList("truck:123")),response.jsonPath().getString("truckId"));
		assertEquals(true, Boolean.parseBoolean(response.jsonPath().getString("transporterApproval")));
		assertEquals(false, Boolean.parseBoolean(response.jsonPath().getString("shipperApproval")));
//		assertEquals(40, Long.parseLong(response.jsonPath().getString("truckLength")));
//		assertEquals(String.valueOf(TruckData.TruckType.OPEN_HALF_BODY), response.jsonPath().getString("truckType"));

	
	}

	@Test
	@Order(7)
	public void getBiddingDataWithIdFailed() throws Exception {

		Response response = RestAssured.given().header("", "").header("accept", "application/json")
				.header("Content-Type", "application/json").get("/bid:7089970").then().extract().response();

		assertEquals(response.asString(), "");
	}

//	@Test
//	@Order(8)
//	public void getTruckDataWithParam_LoadId() throws Exception {
//
//		long lastPageCount = cnt_truckApproved_False % Constants.pageSize;
//		long page = pageNo_truckApproved_False;
//
//		if (lastPageCount >= Constants.pageSize - 1)
//			page++;
//
//		Response response = RestAssured.given().param("pageNo", page).param("truckApproved", false)
//				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
//				.response();
//
//		assertEquals(200, response.statusCode());
//
//		if (lastPageCount <= Constants.pageSize - 2) {
//
//			assertEquals(lastPageCount + 2, response.jsonPath().getList("$").size());
//
//		} else if (lastPageCount == Constants.pageSize - 1) {
//			assertEquals(1, response.jsonPath().getList("$").size());
//		} else if (lastPageCount == Constants.pageSize) {
//			assertEquals(2, response.jsonPath().getList("$").size());
//		}
//
//	}

//	@Test
//	@Order(8)
//	public void getTruckDataWithParam_transporterId() throws Exception {
//
//		long lastPageCount = cnt_TransporterId % Constants.pageSize;
//		long page = pageNo_transporterId;
//
//		if (lastPageCount >= Constants.pageSize - 1)
//			page++;
//
//		Response response = RestAssured.given().param("pageNo", page)
//				.param("transporterId", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69")
//				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
//				.response();
//
//		assertEquals(200, response.statusCode());
//
//		if (lastPageCount <= Constants.pageSize - 2) {
//
//			assertEquals(lastPageCount + 2, response.jsonPath().getList("$").size());
//
//		} else if (lastPageCount == Constants.pageSize - 1) {
//			assertEquals(1, response.jsonPath().getList("$").size());
//		} else if (lastPageCount == Constants.pageSize) {
//			assertEquals(2, response.jsonPath().getList("$").size());
//		}
//
//	}
//
//	@Test
//	@Order(8)
//	public void getTruckDataWithParam_transporterId_truckApproved_False() throws Exception {
//
//		long lastPageCount = cnt_TransporterId_truckApproved_False % Constants.pageSize;
//		long page = pageNo_transporterId_truckApproved_False;
//
//		if (lastPageCount >= Constants.pageSize - 1)
//			page++;
//
//		Response response = RestAssured.given().param("pageNo", page)
//				.param("transporterId", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69")
//				.param("truckApproved", false).header("accept", "application/json")
//				.header("Content-Type", "application/json").get().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//
//		if (lastPageCount <= Constants.pageSize - 2) {
//
//			assertEquals(lastPageCount + 2, response.jsonPath().getList("$").size());
//
//		} else if (lastPageCount == Constants.pageSize - 1) {
//			assertEquals(1, response.jsonPath().getList("$").size());
//		} else if (lastPageCount == Constants.pageSize) {
//			assertEquals(2, response.jsonPath().getList("$").size());
//		}
//
//	}

	@Test
	@Order(9)
	public void updateData1() throws Exception {

		BidPutRequest bidPutRequest = new BidPutRequest( (long) 1000, null,
				null, null, true, null);
		
		String inputJson = mapToJson(bidPutRequest); 

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + id1).then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(Constants.uSuccess, response.jsonPath().getString("status"));
		assertEquals(1000, Long.parseLong(response.jsonPath().getString("rate")));
		// assertEquals("alpha", response.jsonPath().getString("imei"));

	}

	@Test
	@Order(9)
	public void getTruckDataWithParam_LoadId() throws Exception {

		long lastPageCount = cnt_LoadId % Constants.pageSize;
		long page = pageNo_LoadId;

		if (lastPageCount >= Constants.pageSize)
			page++;

		Response response = RestAssured.given().param("pageNo", page).param("loadId", "load:123")
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();

		assertEquals(200, response.statusCode());

		if (lastPageCount <= Constants.pageSize - 1) {

			assertEquals(lastPageCount + 1, response.jsonPath().getList("$").size());

		} else if (lastPageCount == Constants.pageSize) {
			assertEquals(1, response.jsonPath().getList("$").size());
		}

	}
//
//	@Test
//	@Order(9)
//	public void getTruckDataWithParam_transporterId_LoadId() throws Exception {
//
//		long lastPageCount = cnt_TransporterId_LoadId % Constants.pageSize;
//		long page = pageNo_transporterId_LoadId;
//
//		if (lastPageCount >= Constants.pageSize)
//			page++;
//
//		Response response = RestAssured.given().param("pageNo", page)
//				.param("transporterId", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69")
//				.param("truckApproved", true).header("accept", "application/json")
//				.header("Content-Type", "application/json").get().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//
//		if (lastPageCount <= Constants.pageSize - 1) {
//
//			assertEquals(lastPageCount + 1, response.jsonPath().getList("$").size());
//
//		} else if (lastPageCount == Constants.pageSize) {
//			assertEquals(1, response.jsonPath().getList("$").size());
//		}
//
//	}

	@Test
	@Order(10)
	public void updateData2() throws Exception {
		BidPutRequest bidPutRequest = new BidPutRequest( null, BiddingData.UnitValue.PER_TRUCK,
				null, null, null, null);
		
		String inputJson = mapToJson(bidPutRequest); 

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + id1).then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(Constants.uSuccess, response.jsonPath().getString("status"));
		assertEquals(1000, Long.parseLong(response.jsonPath().getString("rate")));	
		assertEquals(String.valueOf(BiddingData.UnitValue.PER_TRUCK), response.jsonPath().getString("unitValue"));	

	
	}

	@Test
	@Order(11)
	public void updateData3() throws Exception {

		BidPutRequest bidPutRequest = new BidPutRequest( (long)1000, BiddingData.UnitValue.PER_TRUCK,
				null, null, null, null);
		
		String inputJson = mapToJson(bidPutRequest); 

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + id1).then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(Constants.uSuccess, response.jsonPath().getString("status"));
		assertEquals(1000, Long.parseLong(response.jsonPath().getString("rate")));	
		assertEquals(String.valueOf(BiddingData.UnitValue.PER_TRUCK), Long.parseLong(response.jsonPath().getString("unitValue")));	
		
		}

//	@Test
//	@Order(12)
//	public void updateData4() throws Exception {
//
//		BidPutRequest bidPutRequest = new BidPutRequest(null, null, 0, "driver:afdge", null, null, null);
//
//		String inputJson = mapToJson(bidPutRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + id1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(Constants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(true, Boolean.parseBoolean(response.jsonPath().getString("truckApproved")));
//		assertEquals("gamma", response.jsonPath().getString("imei"));
//		assertEquals(1000, Long.parseLong(response.jsonPath().getString("passingWeight")));
//		assertEquals("driver:afdge", response.jsonPath().getString("driverId"));
//
//	}
//
//	@Test
//	@Order(13)
//	public void updateData5() throws Exception {
//
//		BidPutRequest bidPutRequest = new BidPutRequest(null, null, 0, null, 10, null, null);
//
//		String inputJson = mapToJson(bidPutRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + id1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(Constants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(true, Boolean.parseBoolean(response.jsonPath().getString("truckApproved")));
//		assertEquals("gamma", response.jsonPath().getString("imei"));
//		assertEquals(1000, Long.parseLong(response.jsonPath().getString("passingWeight")));
//		assertEquals("driver:afdge", response.jsonPath().getString("driverId"));
//		assertEquals(10, Integer.parseInt(response.jsonPath().getString("tyres")));
//	}
//
//	@Test
//	@Order(14)
//	public void updateData6() throws Exception {
//
//		BidPutRequest bidPutRequest = new BidPutRequest(null, null, 0, null, null, (long) 20, null);
//
//		String inputJson = mapToJson(bidPutRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + id1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(Constants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(true, Boolean.parseBoolean(response.jsonPath().getString("truckApproved")));
//		assertEquals("gamma", response.jsonPath().getString("imei"));
//		assertEquals(1000, Long.parseLong(response.jsonPath().getString("passingWeight")));
//		assertEquals("driver:afdge", response.jsonPath().getString("driverId"));
//		assertEquals(10, Integer.parseInt(response.jsonPath().getString("tyres")));
//		assertEquals(20, Long.parseLong(response.jsonPath().getString("truckLength")));
//
//	}
//
//	@Test
//	@Order(15)
//	public void updateData7() throws Exception {
//
//		BidPutRequest bidPutRequest = new BidPutRequest(null, null, 0, null, null, null,
//				TruckData.TruckType.OPEN_FULL_BODY);
//
//		String inputJson = mapToJson(bidPutRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + id1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(Constants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(true, Boolean.parseBoolean(response.jsonPath().getString("truckApproved")));
//		assertEquals("gamma", response.jsonPath().getString("imei"));
//		assertEquals(1000, Long.parseLong(response.jsonPath().getString("passingWeight")));
//		assertEquals("driver:afdge", response.jsonPath().getString("driverId"));
//		assertEquals(10, Integer.parseInt(response.jsonPath().getString("tyres")));
//		assertEquals(20, Long.parseLong(response.jsonPath().getString("truckLength")));
//		assertEquals(String.valueOf(TruckData.TruckType.OPEN_FULL_BODY), response.jsonPath().getString("truckType"));
//	}
//
//	@Test
//	@Order(16)
//	public void updateData8() throws Exception {
//
//		BidPutRequest bidPutRequest = new BidPutRequest(null, "zeta", 100, null, null, null,
//				TruckData.TruckType.FULL_BODY_TRAILER);
//
//		String inputJson = mapToJson(bidPutRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + id1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(Constants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(true, Boolean.parseBoolean(response.jsonPath().getString("truckApproved")));
//		assertEquals("zeta", response.jsonPath().getString("imei"));
//		assertEquals(100, Long.parseLong(response.jsonPath().getString("passingWeight")));
//		assertEquals("driver:afdge", response.jsonPath().getString("driverId"));
//		assertEquals(10, Integer.parseInt(response.jsonPath().getString("tyres")));
//		assertEquals(20, Long.parseLong(response.jsonPath().getString("truckLength")));
//		assertEquals(String.valueOf(TruckData.TruckType.FULL_BODY_TRAILER), response.jsonPath().getString("truckType"));
//	}
//
//	@Test
//	@Order(17)
//	public void updateData9() throws Exception {
//
//		BidPutRequest bidPutRequest = new BidPutRequest(null, null, 0, null, null, null,
//				TruckData.TruckType.OPEN_FULL_BODY);
//
//		String inputJson = mapToJson(bidPutRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + id1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(Constants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(true, Boolean.parseBoolean(response.jsonPath().getString("truckApproved")));
//		assertEquals("zeta", response.jsonPath().getString("imei"));
//		assertEquals(100, Long.parseLong(response.jsonPath().getString("passingWeight")));
//		assertEquals("driver:afdge", response.jsonPath().getString("driverId"));
//		assertEquals(10, Integer.parseInt(response.jsonPath().getString("tyres")));
//		assertEquals(20, Long.parseLong(response.jsonPath().getString("truckLength")));
//		assertEquals(String.valueOf(TruckData.TruckType.OPEN_FULL_BODY), response.jsonPath().getString("truckType"));
//	}

	@Test
	@Order(18)
	public void updateDataFailed() throws Exception {

		BidPutRequest bidPutRequest = new BidPutRequest( (long)1000, BiddingData.UnitValue.PER_TRUCK,
				null, null, null, null);

		String inputJson = mapToJson(bidPutRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/bid:132536").then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(Constants.uDataNotExists, response.jsonPath().getString("status"));

	}

	@AfterAll
	public static void deleteData() throws Exception {

		Response response = RestAssured.given().header("", "").delete("/" + id1).then().extract().response();

		Response response1 = RestAssured.given().header("", "").delete("/" + id2).then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(Constants.dSuccess, response.jsonPath().getString("status"));

		assertEquals(200, response1.statusCode());
		assertEquals(Constants.dSuccess, response1.jsonPath().getString("status"));

	}

	private static String mapToJson(Object object) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

		return objectMapper.writeValueAsString(object);
	}

}
