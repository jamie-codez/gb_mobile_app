package com.greenbay.app.ui.home.models

import com.google.gson.annotations.SerializedName

sealed class ApiResponse<out T>(
    @SerializedName("code") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T?,
) {
    data class Success<out T>(val response: T) : ApiResponse<T>(200, "Success", response)
    data class Loading(val loading: Boolean) : ApiResponse<Nothing>(200, "Loading", null)
    data class Error(val error: String) : ApiResponse<Nothing>(500, error, null)
}

data class Payment(
    @SerializedName("_id") val id: String?=null,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("transactionCode") val transactionCode: String?,
    @SerializedName("amount") val amount: String?,
    @SerializedName("from") val from: String?,
//    @SerializedName("dateCreated") val dateCreated: Long?,
    @SerializedName("verified") val verified: Boolean?,
)

data class PaymentResponse(
    @SerializedName("code") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("payload") val data: Payment
)

data class PaymentListResponse(
    @SerializedName("code") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("payload") val payload: PayResponse
)

data class PayResponse(
    @SerializedName("data") val data:List<Payment>
)

data class PaymentUpdate(
    @SerializedName("from") val from: String?,
    @SerializedName("transactionCode") val transactionCode: String?,
    @SerializedName("payload") val payload: Any?,
)

data class Communication(
    @SerializedName("_id") val id: String?=null,
    @SerializedName("id") val commId: String?=null,
    @SerializedName("to") val to: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("createdBy") val createdBy: String?,
    @SerializedName("dateCreated") val dateCreated: Long?=null,
)

data class CommunicationUpdate(
    @SerializedName("to") val to: String?,
    @SerializedName("payload") val payload: Any?,
)

data class CommunicationResponse(
    @SerializedName("code") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("payload") val data: Communication
)

data class CommunicationListResponse(
    @SerializedName("code") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("payload") val payload: CommListResponse
)

data class CommListResponse(
    @SerializedName("data")val data: List<Communication>
)

data class Task(
    @SerializedName("_id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("scheduledDate") val scheduledDate: String?,
    @SerializedName("createdBy") val createdBy: Int,
    @SerializedName("createdOn") val createdOn: Long,
    @SerializedName("status") val status: Boolean
)

data class TaskResponse(
    @SerializedName("code") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("payload") val data: Task
)

data class ClientTaskResponse(
    @SerializedName("code") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("payload") val payload: TaskData
)

data class TaskData(
    @SerializedName("data") val data: List<ClientTask>
)

data class TaskListResponse(
    @SerializedName("code") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("payload") val payload: TaskList
)
data class TaskList(
    @SerializedName("data") val data: List<Task>
)


data class Tenant(
    @SerializedName("_id") val id: String?,
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("houseNumber") val houseNumber: String?,
    @SerializedName("rent") val rent: String?,
    @SerializedName("deposit") val deposit: String?,
    @SerializedName("floorNumber") val floorNumber: String?,
)


data class TenantResponse(
    @SerializedName("code") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("payload") val data: Tenant
)

data class TenantListResponse(
    @SerializedName("code") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("payload") val data: List<Tenant>
)

data class House(
    @SerializedName("_id") val id: String?,
    @SerializedName("houseNumber") val houseNumber: String?,
    @SerializedName("rent") val rent: String?,
    @SerializedName("deposit") val deposit: String?,
    @SerializedName("floorNumber") val floorNumber: String?,
    @SerializedName("addedBy") val addedBy: String?,
    @SerializedName("createdOn") val createdOn: String?,
    @SerializedName("occupied") val occupied: Boolean?,
)