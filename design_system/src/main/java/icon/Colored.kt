package icon

import com.example.design_system.R

data class Colored(
    val confusedFace: Int,
    val facebook: Int,
    val frowningFace: Int,
    val instagram: Int,
    val iraqFlag: Int,
    val cineVerseLogo: Int,
    val neutralFace: Int,
    val smilingFaceWithSmilingEyes: Int,
    val starStruck: Int,
    val tiktok: Int,
    val ukFlag: Int,
    val xTwitter: Int,
    val youtube: Int,
)

val coloredIcons = Colored(
    confusedFace = R.drawable.colored_confused_face,
    facebook = R.drawable.colored_facebook,
    frowningFace = R.drawable.colored_frowning_face,
    instagram = R.drawable.colored_instagram,
    iraqFlag = R.drawable.colored_iraq_flag,
    cineVerseLogo = R.drawable.colored_cineverse_logo,
    neutralFace = R.drawable.colored_neutral_face,
    smilingFaceWithSmilingEyes = R.drawable.colored_smiling_face_with_smiling_eyes,
    starStruck = R.drawable.colored_star_struck,
    tiktok = R.drawable.colored_tiktok,
    ukFlag = R.drawable.colored_uk_flag,
    xTwitter = R.drawable.colored_x,
    youtube = R.drawable.colored_youtube
)