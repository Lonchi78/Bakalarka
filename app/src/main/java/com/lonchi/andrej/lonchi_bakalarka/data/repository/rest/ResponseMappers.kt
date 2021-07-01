package com.lonchi.andrej.lonchi_bakalarka.data.repository.rest

import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel

fun List<FirebaseVisionImageLabel>.toImageLabelingResponse(): ImageLabelingResponse {
    return ImageLabelingResponse(
        items = this.map {
            ImageLabelingItem(
                item = it.text,
                entityId = it.entityId,
                confidence = it.confidence
            )
        }
    )
}