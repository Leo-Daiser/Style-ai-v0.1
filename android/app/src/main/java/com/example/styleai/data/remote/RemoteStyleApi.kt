package com.example.styleai.data.remote

import com.example.styleai.domain.model.CreditBalance
import com.example.styleai.domain.model.PhotoValidationResult
import com.example.styleai.domain.model.StyleReport
import com.example.styleai.domain.model.VisualizationRequest

/**
 * Interface detailing future secure backend endpoints.
 * All operations here must be routed through a security gateway.
 *
 * TODO Security & Compliance Audits:
 * 1. [Backend Authentication] Secure each API call using bearer tokens (e.g., Firebase Auth or JWT).
 * 2. [Signed Upload URLs] Never transfer raw binary images directly to style engines. Request short-lived presigned cloud storage URLs.
 * 3. [Input Moderation] Run image classification filters server-side to detect and discard forbidden themes (nudity, minors, underwear).
 * 4. [Output Moderation] Restrict AI layout engines from producing non-compliant styles.
 * 5. [Data Minimization & TTL] Delete temporary user images immediately after analysis completes or enforce hard-TTL deletions.
 * 6. [Server-side Credit validation] Implement transactional ledger validations before running intensive style analysis jobs.
 * 7. [Google Play Billing Verification] Validate subscription state receipts server-to-server with Play Store Developer API.
 * 8. [Encryption of Sensitive Data] Encrypt local SQLite storage cache with SQLCipher.
 */
interface RemoteStyleApi {

    /**
     * Submits metadata and file storage references for analysis.
     * Retries and connections should support progressive state changes.
     */
    suspend fun analyzeStyle(
        uploadRequest: StyleAnalysisRequest
    ): StyleReport

    /**
     * Analyzes image safety checklist before loading reports on UI.
     */
    suspend fun validatePhoto(
        photoMetadata: PhotoMetadata
    ): PhotoValidationResult

    /**
     * Triggers a secure style compilation job.
     */
    suspend fun requestVisualization(
        visualizationRequest: VisualizationRequest
    ): VisualizationResponse

    /**
     * Queries status of an active asynchronous style render.
     */
    suspend fun getVisualizationStatus(
        jobId: String
    ): VisualizationJobStatus

    /**
     * Checks subscription credit constraints securely on servers.
     */
    suspend fun getCreditBalance(): CreditBalance

    /**
     * Trigger explicit remote compliance deletion.
     */
    suspend fun deleteServerData(): Boolean
}

enum class StylePrefFit {
    BALANCED,
    LOOSE,
    FITTED
}

// Request & Response Data Models
data class StyleAnalysisRequest(
    val selfieImageUrl: String,
    val fullBodyImageUrl: String,
    val preferredFit: StylePrefFit = StylePrefFit.BALANCED
)

data class PhotoMetadata(
    val key: String,
    val size: Long,
    val mimeType: String
)

data class VisualizationResponse(
    val jobId: String,
    val estimatedWaitSec: Int
)

enum class VisualizationJobStatus {
    PENDING,
    RUNNING,
    COMPLETED,
    FAILED
}
