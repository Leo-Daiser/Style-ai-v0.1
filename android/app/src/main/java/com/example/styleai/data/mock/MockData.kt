package com.example.styleai.data.mock

import com.example.styleai.domain.model.*

object MockData {

    val sampleColorPalette = ColorPalette(
        name = "Soft Autumn inspired palette",
        description = "A warm, muted palette featuring earth tones, sandy neutrals, and soft terracotta notes that harmonize perfectly with gentle warm undertones and lower contrast styling.",
        swatchColors = listOf("#E6D5C3", "#CDB49E", "#8D5B4C", "#5C6B5E", "#3A4D39", "#A87C66"),
        priorityColors = listOf("Warm Terracotta", "Olive Green", "Cream & Sand", "Soft Camel"),
        avoidColors = listOf("Vibrant Neon Lime", "Cold Royal Blue", "Harsh Pure White")
    )

    val sampleColorPaletteRu = ColorPalette(
        name = "Палитра Мягкая Осень",
        description = "Теплая, приглушенная палитра, включающая землистые тона, песчаные нейтральные оттенки и мягкие терракотовые ноты, отлично гармонирующие с мягким теплым подтоном кожи.",
        swatchColors = listOf("#E6D5C3", "#CDB49E", "#8D5B4C", "#5C6B5E", "#3A4D39", "#A87C66"),
        priorityColors = listOf("Теплый терракот", "Оливковый зеленый", "Песочный и кремовый", "Мягкий кэмел"),
        avoidColors = listOf("Яркий неоновый лайм", "Холодный королевский синий", "Резкий чисто-белый")
    )

    val sampleSilhouettes = listOf(
        SilhouetteRecommendation("High-Waisted Trousers", "Creates vertical flow and supports natural waist balance, pairing beautifully with tucked-in knit tops.", "Bottoms"),
        SilhouetteRecommendation("Structured Blazers & Jackets", "Establishes structured shoulder alignment for smart-casual wear, adding crisp geometry to any casual base.", "Outerwear"),
        SilhouetteRecommendation("V-Neck or Open Collar Neckline", "Framing shoulders with flattering diagonal lines to emphasize a breathable, elevated look.", "Tops"),
        SilhouetteRecommendation("Midi-Length Skirts & Dresses", "Drapes softly through the calves to maintain structured proportion, optimal for fluid textiles.", "Dresses")
    )

    val sampleSilhouettesRu = listOf(
        SilhouetteRecommendation("Брюки с высокой посадкой", "Создают вертикальные линии и подчеркивают талию, отлично сочетаются с трикотажным верхом.", "Низ"),
        SilhouetteRecommendation("Структурированные блейзеры", "Придают четкость линии плеч для делового и повседневного стилей.", "Верхняя одежда"),
        SilhouetteRecommendation("V-образный или открытый вырез", "Визуально удлиняет шею и плечи, придавая образу легкость.", "Топы"),
        SilhouetteRecommendation("Юбки и платья миди", "Аккуратно драпируют и сохраняют пропорции фигуры, оптимально для струящихся тканей.", "Платья")
    )

    val sampleStyleDirections = listOf(
        StyleDirection(
            title = "Minimal Chic",
            description = "High-quality essentials, clean alignments, and clean neutral combinations focusing on comfort and functional geometry.",
            bestUseCases = "Professional meetings, city strolling, gallery viewings, high-level casual lunches.",
            recommendedColors = listOf("Oatmeal", "Rich Brown", "Warm Cream"),
            recommendedItems = listOf("Fitted cashmere crewneck", "Slightly tapered tailored trousers", "Minimal leather loafers")
        ),
        StyleDirection(
            title = "Soft Classic",
            description = "Subtle, traditional structure paired with soft fabrics that emphasize balance and timeless symmetry.",
            bestUseCases = "Everyday business casual, casual family dinners, weekend brunches.",
            recommendedColors = listOf("Soft Moss Green", "Warm Taupe", "Deep Olive"),
            recommendedItems = listOf("Slightly structured trench coat", "Midi length dark jersey dress", "Gold fine jewelry accents")
        ),
        StyleDirection(
            title = "Smart Casual",
            description = "Merging business elegance with sporty comfort. Crisp collars matched with soft denims or luxury knits.",
            bestUseCases = "Creative agency workspace, dinner with friends, travel transitions.",
            recommendedColors = listOf("Terracotta brown", "Cinnamon", "Classic Cream"),
            recommendedItems = listOf("Slightly oversized cotton shirt", "Dark-wash straight jeans", "Minimalist white leather sneakers")
        )
    )

    val sampleStyleDirectionsRu = listOf(
        StyleDirection(
            title = "Минималистичный шик",
            description = "Качественные базовые вещи, четкие линии и нейтральные сочетания, ориентированные на комфорт и функциональность.",
            bestUseCases = "Деловые встречи, прогулки по городу, посещение выставок, обеды.",
            recommendedColors = listOf("Овсяный", "Насыщенный коричневый", "Теплый кремовый"),
            recommendedItems = listOf("Кашемировый джемпер", "Укороченные прямые брюки", "Минималистичные кожаные лоферы")
        ),
        StyleDirection(
            title = "Мягкая классика",
            description = "Традиционный крой в сочетании с мягкими тканями, подчеркивающими баланс и вневременную симметрию.",
            bestUseCases = "Повседневный деловой стиль, семейные ужины, бранчи по выходным.",
            recommendedColors = listOf("Мягкий мох", "Теплый тауп", "Глубокий оливковый"),
            recommendedItems = listOf("Классический тренч", "Темное платье-миди из плотного трикотажа", "Лаконичные золотые украшения")
        ),
        StyleDirection(
            title = "Смарт Кэжуал",
            description = "Сочетание элегантности и спортивного комфорта. Четкие воротники в паре с мягким денимом или качественным трикотажем.",
            bestUseCases = "Офис, встречи с друзьями, путешествия.",
            recommendedColors = listOf("Терракотовый коричневый", "Корица", "Классический кремовый"),
            recommendedItems = listOf("Свободная хлопковая рубашка", "Прямые джинсы темного синего оттенка", "Белые кожаные кеды")
        )
    )

    val sampleWardrobeGaps = listOf(
        WardrobeGap("gap_1", "Neutral Blazer", false, "A structured blazer in soft beige or camel to elevate basic tees.", "Essential"),
        WardrobeGap("gap_2", "Straight-Leg Jeans", false, "High-rise, dark-rinse jeans with zero distressing for standard versatility.", "Essential"),
        WardrobeGap("gap_3", "Cream Silk/Cotton Shirt", false, "A clean classic button-down in soft cream instead of stark pure white.", "Essential"),
        WardrobeGap("gap_4", "Tan Leather Belt", false, "Fine textured belt to bind structured blazers and high-waisted trousers.", "Recommended"),
        WardrobeGap("gap_5", "Minimalist White Sneakers", false, "Clean-profile white sneakers to pair with midi dresses and denims.", "Recommended")
    )

    val sampleWardrobeGapsRu = listOf(
        WardrobeGap("gap_1", "Базовый пиджак/блейзер", false, "Структурированный блейзер бежевого или песочного цвета для любого образа.", "Базовый гардероб"),
        WardrobeGap("gap_2", "Прямые джинсы", false, "Классические джинсы средней посадки без потертостей для максимальной практичности.", "Базовый гардероб"),
        WardrobeGap("gap_3", "Кремовая рубашка", false, "Классическая хлопковая рубашка мягкого кремового оттенка вместо резкого белого.", "Базовый гардероб"),
        WardrobeGap("gap_4", "Кожаный ремень", false, "Фактурный кожаный ремень для блейзеров и классических брюк.", "Дополнительно"),
        WardrobeGap("gap_5", "Минималистичные белые кеды", false, "Белые кеды простого силуэта для сочетания с платьями и джинсами.", "Дополнительно")
    )

    val sampleShoppingItems = listOf(
        ShoppingItem("shop_1", 1, "Light Structured Blazer", "$80 - $150", "Prioritize mid-weight wool blends or textured linens in oat or camel."),
        ShoppingItem("shop_2", 2, "Straight-Leg Trousers", "$60 - $110", "Look for a micro-crease front rise and high waist contour."),
        ShoppingItem("shop_3", 3, "Minimal White Sneakers", "$70 - $130", "Opt for zero graphics, low-top designs, and leather finishes."),
        ShoppingItem("shop_4", 4, "Soft Knit Top", "$40 - $80", "Try fine-gauge ribbing in terracotta or sage green with wide cuffs."),
        ShoppingItem("shop_5", 5, "Medium-Size Everyday Bag", "$90 - $200", "Look for a neutral tan leather shoulder bucket or structured flap bag.")
    )

    val sampleShoppingItemsRu = listOf(
        ShoppingItem("shop_1", 1, "Легкий блейзер", "$80 - $150", "Выбирайте ткани из шерсти средней плотности или лен в овсяном/песочном тоне."),
        ShoppingItem("shop_2", 2, "Прямые брюки", "$60 - $110", "Ищите модели с завышенной талией и стрелками."),
        ShoppingItem("shop_3", 3, "Базовые белые кеды", "$70 - $130", "Выбирайте минималистичные кожаные модели без принтов."),
        ShoppingItem("shop_4", 4, "Мягкий трикотажный топ", "$40 - $80", "Попробуйте модели в рубчик оливкового, терракотового или шалфейного оттенков."),
        ShoppingItem("shop_5", 5, "Сумка среднего размера", "$90 - $200", "Идеальна сумка на плечо или структурированная кожаная модель нейтрального оттенка.")
    )

    val sampleWhatToAvoid = listOf(
        "Overly busy distracting patterns with stark visual contrasts that disrupt natural outfits.",
        "Very cold saturated neon accents which block the warmth of soft autumn tones.",
        "Draped oversized layering that isn't balanced by structured anchoring details like high-waisted folds."
    )

    val sampleWhatToAvoidRu = listOf(
        "Слишком броские, контрастные принты, которые нарушают общую гармонию образа.",
        "Очень холодные, кислотно-неоновые оттенки, которые диссонируют с мягкой теплой гаммой.",
        "Объемный оверсайз без акцента на талии или плечах."
    )

    val sampleStyleReport = StyleReport(
        id = "report_test",
        colorPalette = sampleColorPalette,
        silhouettes = sampleSilhouettes,
        styleDirections = sampleStyleDirections,
        wardrobeGaps = sampleWardrobeGaps,
        shoppingList = sampleShoppingItems,
        whatToAvoid = sampleWhatToAvoid
    )

    val sampleStyleReportRu = StyleReport(
        id = "report_test",
        colorPalette = sampleColorPaletteRu,
        silhouettes = sampleSilhouettesRu,
        styleDirections = sampleStyleDirectionsRu,
        wardrobeGaps = sampleWardrobeGapsRu,
        shoppingList = sampleShoppingItemsRu,
        whatToAvoid = sampleWhatToAvoidRu
    )

    val sampleOutfitIdeas = listOf(
        OutfitIdea("outfit_1", "Effortless City Uniform", "Everyday", "Minimal", "Autumn", 
            listOf("Camel blazer", "Cream ribbed crewneck", "Slightly tapered oatmeal pants"),
            listOf("#E6D5C3", "#CDB49E", "#8D5B4C"), "outfit_pic_1", false),
        OutfitIdea("outfit_2", "Gallery Gala Evening", "Event", "Feminine", "Spring", 
            listOf("Midi ribbed silk dress in terracotta", "Light beige structured trench", "Nude flats"),
            listOf("#CD8D7A", "#E6D5C3", "#CDB49E"), "outfit_pic_2", false),
        OutfitIdea("outfit_3", "Creative Workspace Blend", "Office", "Smart casual", "Autumn", 
            listOf("Wide-leg dark denim", "Slightly structured terracotta shirt", "Minimal white sneakers"),
            listOf("#3A4D39", "#8D5B4C", "#E6D5C3"), "outfit_pic_3", false),
        OutfitIdea("outfit_4", "Timeless Boulevard", "Travel", "Classic", "Winter", 
            listOf("Camel drape wool coat", "Fitted black highneck", "Classic indigo denim"),
            listOf("#5C6B5E", "#CDB49E", "#3A4D39"), "outfit_pic_4", false),
        OutfitIdea("outfit_5", "Casual Coffee Encounter", "Everyday", "Streetwear", "Summer", 
            listOf("Relaxed beige short pants", "Sage green organic cotton tee", "Minimal cream sliders"),
            listOf("#E6D5C3", "#5C6B5E", "#8D5B4C"), "outfit_pic_5", false),
        OutfitIdea("outfit_6", "Bistro Candlelit Dinner", "Date", "Feminine", "Autumn", 
            listOf("Deep olive structured shirt dress", "Tailored tan leather belt", "Muted brown ankle boots"),
            listOf("#3A4D39", "#8D5B4C", "#CDB49E"), "outfit_pic_6", false)
    )

    val samplePurchasePlans = listOf(
        PurchasePlan("plan_free", "Free Basic Plan", "Explore early style tools without purchases.", "Free", 
            listOf("Basic style preview guidelines", "View limited outfit collections", "Local image security logs")),
        PurchasePlan("plan_full_report", "Complete Style Report", "Gain lifetime insights with a individual evaluation report.", "$9.99", 
            listOf("6-color Soft Autumn Swatches analysis", "Silhouettes, best fits & prioritized items", "Step-by-step wardrobe gaps checkboxes", "Full collection safe outfits"), false),
        PurchasePlan("plan_pro_sub", "StyleAI Pro Subscription", "Continuous style optimization dynamically adjusted.", "$9.99/mo", 
            listOf("Daily custom outfit formulations", "Interactive seasonal style capsule guides", "Active 'Should I buy this?' barcode evaluator", "Priority customer care support"), true)
    )

    val sampleCreditPacks = listOf(
        PurchasePlan("pack_10", "10 Rendering Credits", "Unlock 10 extra style visions.", "$4.99", listOf("10 Safe Outfit Visualizations", "One-click save to library")),
        PurchasePlan("pack_30", "30 Rendering Credits", "Unlock 30 extra style visions.", "$9.99", listOf("30 Safe Outfit Visualizations", "One-click save to library")),
        PurchasePlan("pack_100", "100 Rendering Credits", "Unlock 100 extra style visions.", "$24.99", listOf("100 Safe Outfit Visualizations", "Double resolution outputs"))
    )

    val sampleHistoryItems = listOf(
        HistoryItem("hist_1", System.currentTimeMillis() - 86400000 * 5, "My Soft Autumn Report", "Soft Autumn Swatch", 3),
        HistoryItem("hist_2", System.currentTimeMillis() - 86400000 * 12, "Spring Transitional Guide", "Soft Warm Pastel", 1)
    )
}
