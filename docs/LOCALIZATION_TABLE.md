# StyleAI Localization Table

StyleAI supports switching languages on the fly between English and Russian without restarting the application. The table below represents the resource string keys utilized throughout the system.

---

## 🌎 Global Localization Maps

| Key | English String (EN) | Russian String (RU) | Screen |
| :--- | :--- | :--- | :--- |
| **`appTitle`** | StyleAI | StyleAI | Header, Splash |
| **`splashSubtitle`** | Your Private AI Style Assistant | Ваш приватный AI-стилист | Splash |
| **`skipOnboarding`** | Skip Onboarding | Пропустить | Onboarding |
| **`continue`** | Continue | Продолжить | Onboarding |
| **`getStarted`** | Get Started | Начать | Onboarding |
| **`privacyConsent`** | Privacy & Consent | Конфиденциальность и согласие | Consent |
| **`consentSubtitle`** | To maintain strict legal safety protocols, please verify and consent to the operations below: | Для соблюдения строгих правил безопасности, пожалуйста, подтвердите согласие со следующими пунктами: | Consent |
| **`consentCheck1`** | I confirm that the physical uploaded image is of myself or that I possess explicit legal permission from the subject. | Я подтверждаю, что загружаемое изображение принадлежит мне или у меня есть явное разрешение от владельца. | Consent |
| **`consentCheck2`** | I acknowledge that the style report contains clothing matches and references, not medical or body therapy advice. | Я согласен с тем, что стилистический отчет содержит рекомендации по одежде и сочетаниям, а не медицинские рекомендации. | Consent |
| **`consentCheck3`** | Raw photos are not stored by default. In-memory photo transient data minimizer. | Исходные фотографии по умолчанию не сохраняются. Минимизация данных и обработка только в оперативной памяти. | Consent |
| **`confirmAndContinue`** | Confirm and Continue | Подтвердить и продолжить | Consent |
| **`homeTitle`** | StyleAI | StyleAI | Home |
| **`homeSubtitle`** | Make better wardrobe decisions. | Принимайте лучшие решения для гардероба. | Home |
| **`homePrimaryTitle`** | Should I buy this? | Стоит ли покупать? | Home |
| **`homePrimarySubtitle`** | Check if an item is worth buying before it becomes another unused piece. | Проверьте вещь до покупки, чтобы она не стала очередной ненужной покупкой. | Home |
| **`homePrimaryBtn`** | Check an item | Проверить вещь | Home |
| **`homeWearTitle`** | What should I wear? | Что надеть? | Home |
| **`homeWearSubtitle`** | Get outfit ideas from saved looks, style rules, and capsule logic. | Получите идеи образов на основе сохраненных решений, правил стиля и капсулы. | Home |
| **`homeWearBtn`** | Get outfit ideas | Подобрать образ | Home |
| **`homeSecTitleNoProfile`** | Create style profile | Создать стиль-профиль | Home |
| **`homeSecSubtitleNoProfile`** | Optional: unlock palette, silhouettes, and capsule recommendations. | Необязательно: откройте палитру, силуэты и рекомендации по капсуле. | Home |
| **`homeSecBtnNoProfile`** | Create profile | Создать профиль | Home |
| **`homeSecTitleProfileReady`** | Your style profile is ready | Ваш стиль-профиль готов | Home |
| **`homeSecSubtitleProfileReady`** | Your Soft Classic palette and silhouette matrix are active. | Ваша палитра Мягкой Осени и матрица силуэтов активны. | Home |
| **`homeSecBtnProfileReady`** | Open report | Открыть отчет | Home |
| **`privacyModeTitle`** | Privacy-first MVP | Приватный MVP | Home, Consent |
| **`privacyModeText`** | No internet permission. Raw photos are not stored by default. You can use shopping checks without uploading face/body photos. | Нет доступа к интернету. Исходные фото по умолчанию не сохраняются. Проверку покупок можно использовать без загрузки лица и тела. | Home, Consent |
| **`shouldIBuyTitle`** | Should I Buy This? | Стоит ли покупать? | Shopping Check |
| **`shouldIBuySubtitle`** | Evaluate any prospective garment photo or screenshot against your custom Style Report profile. | Проверьте совместимость любой одежды по фото или снимку экрана с вашим профилем отчета по стилю. | Shopping Check |
| **`categoryLabel`** | Select Clothing Category | Выберите категорию одежды | Shopping Check |
| **`verdictLabel`** | Verdict | Вердикт | Shopping Check |
| **`verdictGood`** | GOOD MATCH | РЕКОМЕНДУЕМАЯ ПОКУПКА | Shopping Check, Decisions |
| **`verdictMaybe`** | MAYBE (PROCEED CAREFULLY) | ПОД ВОПРОСОМ (ОСТОРОЖНО) | Shopping Check, Decisions |
| **`verdictSkip`** | SKIP (POOR MATCH) | ПРОПУСТИТЬ (НЕ ПОДХОДИТ) | Shopping Check, Decisions |
| **`visSafetyNotice`** | Visualizations are style references, not exact clothing fit simulations. | Визуализации показывают стилевое направление, а не точную посадку одежды. | Looks board |
| **`paywallWarningUnlimited`** | No unlimited AI image generation supported. | Без безлимитной AI-генерации изображений. | Paywall Screen |
| **`paywallWarningSafety`** | Credits are instantly refunded if a visualization fails automated safety checks. | Кредиты возвращаются, если визуализация не проходит проверку безопасности. | Paywall Screen |

---

## 📖 Stylistic tone in Russian language (Пособие по тону речи)

When implementing Russian language features:
* **Formality:** Always use respectful, polite form (`Вы`, `Ваш`, `Принимайте`, `Загрузите`).
* **Technical Terms:** Translate labels directly without resorting to slang. Use "пробелы гардероба" for wardrobe gaps and "стиль-паспорт вещи" for garment checks.
* **Safety & Inclusivity:** Never use body-shaming words; describe styling strictly around physical geometry ("геометрия силуэта"), proportions, and symmetry lines.
