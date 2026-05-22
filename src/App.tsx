import { useState, useEffect } from "react";
import { 
  Camera, Upload, Palette, CheckSquare, History, Settings, 
  ShieldCheck, AlertTriangle, CreditCard, Lock, RefreshCw, 
  Sliders, Download, Sparkles, Trash2, ArrowRight, Eye, BookOpen,
  Wifi, Battery, ShieldAlert, Heart, CheckCircle, Info, ChevronRight, HelpCircle, Laptop
} from "lucide-react";
import { kotlinFiles } from "./kotlinFiles";

// Structured translations
const STRINGS = {
  EN: {
    appTitle: "StyleAI",
    splashSubtitle: "Your Private AI Style Assistant",
    skipOnboarding: "Skip Onboarding",
    continue: "Continue",
    getStarted: "Get Started",
    onboardingTitle1: "Discover your personal style",
    onboardingDesc1: "Get a tailored style report card generated based on your real facial pigmentations, silhouette structures, and individual aesthetic choices.",
    onboardingTitle2: "Build better outfits",
    onboardingDesc2: "Identify perfect garment shapes, curated blazers, safe combination color spectrums, and close high-priority wardrobe gaps effortlessly.",
    onboardingTitle3: "Privacy-First by design",
    onboardingDesc3: "Your uploaded pixels are parsed purely in-memory. They are not recorded, not transmitted, and not shared. Safe, reliable, and secure.",
    privacyConsent: "Privacy & Consent",
    consentSubtitle: "To maintain strict legal safety protocols, please verify and consent to the operations below:",
    consentCheck1: "I confirm that the physical uploaded image is of myself or that I possess explicit legal permission from the subject.",
    consentCheck2: "I acknowledge that the style report contains clothing matches and references, not medical or body therapy advice.",
    consentCheck3: "Raw photos are not stored by default. In-memory photo transient data minimizer.",
    confirmAndContinue: "Confirm and Continue",
    uploadTitle: "Upload Photos",
    uploadSubtitle: "Upload selfies and body silhouettes to analyze style.",
    selfieLabel: "1. Face/Selfie Photo",
    fullBodyLabel: "2. Full Body Contour",
    startAnalysis: "Initiate Style Analysis",
    warningBlurry: "Poor lighting detected. Please stand closer to a window, turning face to natural sun vectors for optimal palette evaluations.",
    validationError: "Safety filter trigger: Blocked. Only single adult portraits are permitted.",
    loadingStep1: "Photo Safety & Moderation Checks...",
    loadingStep2: "Scanning skin pigments & season colors...",
    loadingStep3: "Measuring shoulder alignments & fits...",
    loadingStep4: "Curating Soft Classic style paths...",
    loadingStep5: "Cross-referencing wardrobe gaps lists...",
    loadingStep6: "Assembling recommendations report...",
    reportTitle: "Style Analysis Report",
    colorPaletteSection: "Recommended Color Palette",
    silhouettesSection: "Suggested Silhouettes",
    styleDirectionsSection: "Style Directions",
    wardrobeGapsSection: "Wardrobe Gaps Checklist",
    shoppingListSection: "Priority Shopping List",
    whatToAvoidSection: "Avoid Guide (Safety Note)",
    bodySafetyNotice: "We avoid labels or body-shaming terms. Style targets personal geometry.",
    outfitBoard: "Outfit Board",
    generateLayoutsSafely: "Generate reference layouts safely",
    occasionLabel: "Occasion",
    styleLabel: "Style",
    seasonLabel: "Season",
    formalityLabel: "Formality",
    colorDirectionLabel: "Color Direction",
    visSafetyNotice: "Visualizations are style references, not exact clothing fit simulations. Unsafe or non-consensual image use is not allowed.",
    renderOutfit: "Render Outfit (1 Credit)",
    unlockPremium: "Unlock StyleAI Premium",
    unlockSubtitle: "Unlock full seasonal guides and custom styling boards",
    paywallCompleteReport: "Complete Style Report",
    paywallSubPlan: "Pro Monthly Plan",
    paywallCreditsPack: "Buy 10 Outfit Credits",
    goBackToOutfits: "Go Back to Outfits",
    historyTitle: "Local History Log",
    historySubtitle: "Manage device caches and saved items logs",
    activeReports: "Active Reports Collection",
    savedLooks: "Saved Looks list",
    emptyHistory: "No generated style indices stored. Upload photos to generate colors.",
    appSettings: "App Settings",
    settingsSubtitle: "Fine-tune privacy thresholds, data exports, and cache minimizations.",
    settingsLanguageSection: "Language / Язык",
    settingsBtnWipeLooks: "Delete Style Data Only",
    settingsBtnResetOnboarding: "Reset App Onboarding",
    settingsBtnWipeAll: "Purge All Local Data & Reset",
    settingsExportButton: "Export Style Report (JSON)",
    settingsExporting: "Generating Export Pack...",
    settingsDismiss: "Dismiss",
    settingsPurgePrompt: "Purge Local Cache?",
    settingsPurgeDesc: "This will completely discard all saved color swatches, wardrobe checklist milestones, saved reports and outfits. This is irreversible.",
    confirmPurge: "Confirm Purge"
  },
  RU: {
    appTitle: "StyleAI",
    splashSubtitle: "Ваш приватный AI-стилист",
    skipOnboarding: "Пропустить",
    continue: "Продолжить",
    getStarted: "Начать",
    onboardingTitle1: "Откройте свой персональный стиль",
    onboardingDesc1: "Получите индивидуальную карту стиля на основе цветотипа лица, особенностей силуэта и ваших личных эстетических предпочтений.",
    onboardingTitle2: "Составляйте лучшие образы",
    onboardingDesc2: "Легко подбирайте идеальные фасоны, жакеты, гармоничные цветовые схемы и восполняйте пробелы в гардеробе.",
    onboardingTitle3: "Конфиденциальность на первом месте",
    onboardingDesc3: "Ваши фотографии обрабатываются исключительно в оперативной памяти. Они не сохраняются, не передаются и не передаются третьим лицам. Безопасно и надежно.",
    privacyConsent: "Конфиденциальность и согласие",
    consentSubtitle: "Для соблюдения строгих правил безопасности, пожалуйста, подтвердите согласие со следующими пунктами:",
    consentCheck1: "Я подтверждаю, что загружаемое изображение принадлежит мне или у меня есть явное разрешение от владельца.",
    consentCheck2: "Я согласен с тем, что стилистический отчет содержит рекомендации по одежде и сочетаниям, а не медицинские рекомендации.",
    consentCheck3: "Исходные фотографии по умолчанию не сохраняются. Минимизация данных и обработка только в оперативной памяти.",
    confirmAndContinue: "Подтвердить и продолжить",
    uploadTitle: "Загрузка фотографий",
    uploadSubtitle: "Загрузите селфи и силуэт тела для анализа стиля.",
    selfieLabel: "1. Фото лица / Селфи",
    fullBodyLabel: "2. Силуэт в полный рост",
    startAnalysis: "Начать анализ стиля",
    warningBlurry: "Обнаружено плохое освещение. Пожалуйста, встаньте ближе к окну, повернувшись к естественному свету, для лучшего анализа палитры.",
    validationError: "Проверка безопасности: Найден неподдерживаемый контент. Разрешены только одиночные взрослые портреты.",
    loadingStep1: "Проверка безопасности и модерация...",
    loadingStep2: "Анализ пигментов и сезонных цветов...",
    loadingStep3: "Измерение пропорций и линий плеч...",
    loadingStep4: "Разработка стиля Soft Classic...",
    loadingStep5: "Составление списков гардероба...",
    loadingStep6: "Формирование персонального отчета...",
    reportTitle: "Отчет по стилю",
    colorPaletteSection: "Рекомендуемая палитра цветов",
    silhouettesSection: "Рекомендуемые силуэты",
    styleDirectionsSection: "Стилистические направления",
    wardrobeGapsSection: "Пробелы в гардеробе",
    shoppingListSection: "Приоритетный список покупок",
    whatToAvoidSection: "Чего рекомендуется избегать (Заметка безопасности)",
    bodySafetyNotice: "Мы не используем ярлыки и критику фигуры. Стилизация направлена на гармонию геометрии.",
    outfitBoard: "Визуализации образов",
    generateLayoutsSafely: "Создавайте мудборды стиля на основе своего отчета",
    occasionLabel: "Событие",
    styleLabel: "Стиль",
    seasonLabel: "Сезон",
    formalityLabel: "Формальность",
    colorDirectionLabel: "Направление цвета",
    visSafetyNotice: "Визуализации показывают стилевое направление, а не точную посадку одежды. Небезопасное использование изображений и обработка фото без согласия запрещены.",
    renderOutfit: "Сгенерировать образ (1 Кредит)",
    unlockPremium: "Разблокировать StyleAI Premium",
    unlockSubtitle: "Разблокируйте полные сезонные гиды и интерактивные подборы",
    paywallCompleteReport: "Полный отчет по стилю",
    paywallSubPlan: "Ежемесячная подписка Pro",
    paywallCreditsPack: "Купить 10 кредитов образов",
    goBackToOutfits: "Вернуться к образам",
    historyTitle: "Локальная история",
    historySubtitle: "Управление кешем устройства и сохраненными образами",
    activeReports: "Коллекция активных отчетов",
    savedLooks: "Сохраненные образы",
    emptyHistory: "Сохраненные отчеты отсутствуют. Загрузите фото для генерации.",
    appSettings: "Настройки приложения",
    settingsSubtitle: "Параметры конфиденциальности, экспорта данных и очистки кеша.",
    settingsLanguageSection: "Язык / Language",
    settingsBtnWipeLooks: "Удалить только данные о стиле",
    settingsBtnResetOnboarding: "Сбросить онбординг приложения",
    settingsBtnWipeAll: "Удалить все данные и очистить кеш",
    settingsExportButton: "Экспортировать отчет (JSON)",
    settingsExporting: "Экспорт пакета...",
    settingsDismiss: "Скрыть",
    settingsPurgePrompt: "Очистить локальный кеш?",
    settingsPurgeDesc: "Это полностью удалит сохраненные палитры, пробелы гардероба и отчеты. Действие необратимо.",
    confirmPurge: "Подтвердить удаление"
  }
};

const INITIAL_OUTFITS = [
  { id: "outfit_1", title: "Effortless City Uniform", occasion: "Everyday", style: "Minimal", season: "Autumn", formality: "Polished", colorDir: "Neutral", items: ["Camel blazer", "Cream ribbed crewneck", "Tapered oatmeal pants"], colors: ["#DCA494", "#E6D5C3", "#8D5B4C"], isSaved: false },
  { id: "outfit_2", title: "Gallery Gala Evening", occasion: "Event", style: "Feminine", season: "Spring", formality: "Formal", colorDir: "Warm", items: ["Midi ribbed silk dress in terracotta", "Light beige structured trench", "Nude flats"], colors: ["#CD8D7A", "#E6D5C3", "#CDB49E"], isSaved: false },
  { id: "outfit_3", title: "Creative Workspace Blend", occasion: "Office", style: "Smart casual", season: "Autumn", formality: "Polished", colorDir: "Soft", items: ["Wide-leg dark denim", "Slightly structured terracotta shirt", "Minimal white sneakers"], colors: ["#3A4D39", "#8D5B4C", "#E6D5C3"], isSaved: false },
  { id: "outfit_4", title: "Timeless Boulevard", occasion: "Travel", style: "Classic", season: "Winter", formality: "Formal", colorDir: "Cool", items: ["Camel drape wool coat", "Fitted black highneck", "Classic indigo denim"], colors: ["#5C6B5E", "#CDB49E", "#3A4D39"], isSaved: false },
  { id: "outfit_5", title: "Casual Coffee Encounter", occasion: "Everyday", style: "Streetwear", season: "Summer", formality: "Casual", colorDir: "Contrast", items: ["Relaxed beige short pants", "Sage green cotton tee", "Minimal cream sliders"], colors: ["#E6D5C3", "#5C6B5E", "#8D5B4C"], isSaved: false },
  { id: "outfit_6", title: "Bistro Candlelit Dinner", occasion: "Date", style: "Feminine", season: "Autumn", formality: "Polished", colorDir: "Warm", items: ["Deep olive structured dress", "Tailored tan leather belt", "Muted brown ankle boots"], colors: ["#3A4D39", "#8D5B4C", "#CDB49E"], isSaved: false }
];

export default function App() {
  // Manual App Language Switcher
  const [appLang, setAppLang] = useState<"EN" | "RU">("EN");
  const s = STRINGS[appLang];

  // Mobile Device screens state
  const [currentScreen, setCurrentScreen] = useState<
    "splash" | "onboarding" | "consent" | "upload" | "analysis" | "report" | "looks" | "history" | "settings" | "paywall"
  >("splash");
  
  const [onboardingSlide, setOnboardingSlide] = useState(0);
  const [consentState, setConsentState] = useState({
    permission: false,
    disclaimer: false,
    noStore: false
  });

  // Photo state
  const [selfiePhoto, setSelfiePhoto] = useState<{ uri: string; type: "safe" | "blurry" | "restricted" } | null>(null);
  const [bodyPhoto, setBodyPhoto] = useState<{ uri: string; type: "safe" | "blurry" | "restricted" } | null>(null);
  const [photoValidationError, setPhotoValidationError] = useState<string | null>(null);
  const [photoValidationWarnings, setPhotoValidationWarnings] = useState<string[]>([]);

  // Stepper calculations
  const [isAnalyzing, setIsAnalyzing] = useState(false);
  const [analysisProgress, setAnalysisProgress] = useState(0);
  const [analysisStepText, setAnalysisStepText] = useState("");
  const [hasGeneratedReport, setHasGeneratedReport] = useState(false);
  
  const [creditBalance, setCreditBalance] = useState({ credits: 3, isPro: false });
  const [outfits, setOutfits] = useState(INITIAL_OUTFITS);
  const [savedLooks, setSavedLooks] = useState<{ id: string; title: string; color: string; date: string }[]>([]);
  
  // CONTROLLED SELECTORS FOR VISUAL BOARD (No free-text prompt as requested)
  const [selectedOccasion, setSelectedOccasion] = useState<"Everyday" | "Office" | "Date" | "Travel" | "Event">("Everyday");
  const [selectedStyle, setSelectedStyle] = useState<"Minimal" | "Classic" | "Smart casual" | "Feminine" | "Streetwear">("Minimal");
  const [selectedSeason, setSelectedSeason] = useState<"Spring" | "Summer" | "Autumn" | "Winter">("Autumn");
  const [selectedFormality, setSelectedFormality] = useState<"Casual" | "Polished" | "Formal">("Polished");
  const [selectedColorDir, setSelectedColorDir] = useState<"Neutral" | "Warm" | "Cool" | "Soft" | "Contrast">("Neutral");

  const [generationSuccessAlert, setGenerationSuccessAlert] = useState(false);

  // Settings feed triggers
  const [settingsFeedback, setSettingsFeedback] = useState<string | null>(null);

  // Confirmation dialog state controllers
  const [dialogOpen, setDialogOpen] = useState<"none" | "styleData" | "onboarding">("none");

  // App version clicks for the hidden developer view
  const [versionTapCount, setVersionTapCount] = useState(0);
  const [devReadinessVisible, setDevReadinessVisible] = useState(false);

  // Code Tab state for visual syncing
  const [activeCodeKey, setActiveCodeKey] = useState<string>("models");
  const [simulatorThemeMode, setSimulatorThemeMode] = useState<"light" | "dark">("light");

  // Splash auto timing jump
  useEffect(() => {
    if (currentScreen === "splash") {
      const timer = setTimeout(() => {
        setCurrentScreen("onboarding");
      }, 2200);
      return () => clearTimeout(timer);
    }
  }, [currentScreen]);

  // Sync simulator screen coordinates to code viewer tabs
  useEffect(() => {
    if (currentScreen === "onboarding" || currentScreen === "consent") {
      setActiveCodeKey("localization");
    } else if (currentScreen === "upload" || currentScreen === "analysis") {
      setActiveCodeKey("privacy");
    } else if (currentScreen === "report") {
      setActiveCodeKey("models");
    } else if (currentScreen === "looks") {
      setActiveCodeKey("visualization");
    } else if (currentScreen === "history") {
      setActiveCodeKey("privacy");
    } else if (currentScreen === "settings") {
      setActiveCodeKey("settings");
    }
  }, [currentScreen]);

  const allConsented = consentState.permission && consentState.disclaimer && consentState.noStore;

  // Mock Photo selector rules
  const handleSelectPhoto = (photoTarget: "selfie" | "body", optionType: "safe" | "blurry" | "restricted") => {
    setPhotoValidationError(null);
    setPhotoValidationWarnings([]);

    const uriText = photoTarget === "selfie" 
      ? (optionType === "safe" ? "selfie_portrait_highres.jpg" : optionType === "blurry" ? "selfie_dark_room.jpg" : "selfie_restricted_minor.jpg")
      : (optionType === "safe" ? "fullbody_alignment_chic.jpg" : optionType === "blurry" ? "fullbody_shaky_focus.jpg" : "fullbody_group_bathing.jpg");

    const photoObject = { uri: uriText, type: optionType };

    if (photoTarget === "selfie") {
      setSelfiePhoto(photoObject);
      runValidationSimulation(photoObject);
    } else {
      setBodyPhoto(photoObject);
      runValidationSimulation(photoObject);
    }
  };

  const runValidationSimulation = (photo: { uri: string; type: "safe" | "blurry" | "restricted" }) => {
    if (photo.type === "blurry") {
      setPhotoValidationWarnings([s.warningBlurry]);
    } else if (photo.type === "restricted") {
      setPhotoValidationError(s.validationError);
    }
  };

  // Stepper processor
  const runAnalysisSimulation = () => {
    if (!selfiePhoto || !bodyPhoto || photoValidationError) return;
    setCurrentScreen("analysis");
    setAnalysisProgress(0);
    
    const steps = [
      { text: s.loadingStep1, ms: 600 },
      { text: s.loadingStep2, ms: 1200 },
      { text: s.loadingStep3, ms: 1800 },
      { text: s.loadingStep4, ms: 2400 },
      { text: s.loadingStep5, ms: 3000 },
      { text: s.loadingStep6, ms: 3600 }
    ];

    steps.forEach((step, idx) => {
      setTimeout(() => {
        setAnalysisStepText(step.text);
        setAnalysisProgress(Math.floor(((idx + 1) * 100) / steps.length));
        if (idx === steps.length - 1) {
          setTimeout(() => {
            setHasGeneratedReport(true);
            setCurrentScreen("report");
          }, 600);
        }
      }, step.ms);
    });
  };

  // Toggle saving
  const toggleSaveOutfit = (id: string) => {
    setOutfits(current => current.map(item => {
      if (item.id === id) {
        const nextSaved = !item.isSaved;
        if (nextSaved) {
          setSavedLooks(prev => {
            const filtered = prev.filter(look => look.id !== `look_${item.id}`);
            return [
              { id: `look_${item.id}`, title: item.title, color: item.colors[0], date: "Today" },
              ...filtered
            ];
          });
        } else {
          setSavedLooks(prev => prev.filter(look => look.id !== `look_${item.id}`));
        }
        return { ...item, isSaved: nextSaved };
      }
      return item;
    }));
  };

  // Controlled render triggers
  const triggerSafeVisualization = () => {
    if (!creditBalance.isPro && creditBalance.credits <= 0) {
      setCurrentScreen("paywall");
      return;
    }

    setCreditBalance(prev => ({ ...prev, credits: Math.max(0, prev.credits - 1) }));
    setGenerationSuccessAlert(true);

    // Formulate a clean matching outfit idea based solely on safe selected parameters
    const generatedOutfit = {
      id: `outfit_gen_${Date.now()}`,
      title: `${selectedStyle} ${selectedOccasion} Outfit`,
      occasion: selectedOccasion,
      style: selectedStyle,
      season: selectedSeason,
      formality: selectedFormality,
      colorDir: selectedColorDir,
      items: [`Curated ${selectedStyle} piece`, `${selectedColorDir} harmonized jacket`, "Structured matching footwear"],
      colors: selectedColorDir === "Warm" ? ["#8D5B4C", "#E6D5C3"] : ["#3A4D39", "#CDB49E"],
      isSaved: false
    };

    setOutfits(prev => [generatedOutfit, ...prev]);
    setTimeout(() => setGenerationSuccessAlert(false), 4000);
  };

  // Reset operations
  const handleWipeStyleDataOnly = () => {
    // Keeps language and payments intact
    setSelfiePhoto(null);
    setBodyPhoto(null);
    setHasGeneratedReport(false);
    setSavedLooks([]);
    setOutfits(INITIAL_OUTFITS);
    setSettingsFeedback(appLang === "EN" ? "Deleted local style profiles, wardrobe checklists, and saved looks cache." : "Данные о стиле, галочки гардероба и кеш сохраненных образов удалены.");
    setTimeout(() => setSettingsFeedback(null), 4000);
  };

  const handleResetAppOnboardingOnly = () => {
    setConsentState({ permission: false, disclaimer: false, noStore: false });
    setSelfiePhoto(null);
    setBodyPhoto(null);
    setPhotoValidationError(null);
    setPhotoValidationWarnings([]);
    setHasGeneratedReport(false);
    setCurrentScreen("onboarding");
    setOnboardingSlide(0);
    setSettingsFeedback(appLang === "EN" ? "Onboarding sliders and privacy gate consents reset completely." : "Слайды онбординга и соглашения конфиденциальности полностью сброшены.");
    setTimeout(() => setSettingsFeedback(null), 4000);
  };

  const handleFullReset = () => {
    setAppLang("EN");
    setConsentState({ permission: false, disclaimer: false, noStore: false });
    setSelfiePhoto(null);
    setBodyPhoto(null);
    setPhotoValidationError(null);
    setPhotoValidationWarnings([]);
    setHasGeneratedReport(false);
    setSavedLooks([]);
    setCreditBalance({ credits: 3, isPro: false });
    setOutfits(INITIAL_OUTFITS);
    setCurrentScreen("onboarding");
    setOnboardingSlide(0);
    setVersionTapCount(0);
    setDevReadinessVisible(false);
    setSettingsFeedback("Application state wipe & local database simulator fully emptied.");
    setTimeout(() => setSettingsFeedback(null), 4000);
  };

  const handleVersionTapped = () => {
    const nextCount = versionTapCount + 1;
    setVersionTapCount(nextCount);
    if (nextCount >= 5) {
      setDevReadinessVisible(true);
    }
  };

  return (
    <div className="min-h-screen bg-slate-900 text-slate-100 font-sans flex flex-col antialiased">
      {/* Upper Navigation Header Bar */}
      <header className="border-b border-slate-800 bg-slate-950 px-6 py-4 flex items-center justify-between shadow-lg shrink-0">
        <div className="flex items-center gap-3">
          <Palette className="h-6 w-6 text-emerald-400" />
          <div>
            <span className="font-mono text-[10px] text-slate-500 font-semibold tracking-widest uppercase">Android Architecture MVP</span>
            <h1 className="text-lg font-bold text-slate-200">StyleAI Developer Playground</h1>
          </div>
        </div>
        <div className="flex items-center gap-4">
          <div className="hidden sm:flex items-center gap-2 bg-slate-800/60 px-3 py-1.5 rounded-md border border-slate-700/50">
            <Lock className="text-emerald-400 h-3.5 w-3.5" />
            <span className="text-xs text-slate-300 font-mono">Bilingual Safe Mode</span>
          </div>
          <div className="flex gap-1.5 bg-slate-850 p-1 rounded-lg border border-slate-755">
            <button 
              onClick={() => setAppLang("EN")} 
              className={`px-2.5 py-1 text-xs font-bold rounded transition ${appLang === "EN" ? "bg-emerald-500 text-slate-950" : "text-slate-400 hover:text-slate-200"}`}
            >
              EN
            </button>
            <button 
              onClick={() => setAppLang("RU")} 
              className={`px-2.5 py-1 text-xs font-bold rounded transition ${appLang === "RU" ? "bg-emerald-500 text-slate-950" : "text-slate-400 hover:text-slate-200"}`}
            >
              RU
            </button>
          </div>
        </div>
      </header>

      {/* Main Split Layout Panel */}
      <main className="flex-1 overflow-hidden flex flex-col lg:flex-row">
        
        {/* LEFT COLUMN: Physical Android Device Emulator Canvas */}
        <section className="flex-1 bg-slate-950 p-4 lg:p-6 flex flex-col items-center justify-start overflow-y-auto border-r border-slate-800 scrollbar-none">
          
          {/* Controls: Device mode and shortcuts */}
          <div className="w-full max-w-[385px] mb-3 flex items-center justify-between text-xs bg-slate-900 p-2.5 rounded-lg border border-slate-800">
            <div className="flex gap-2">
              <span className="text-slate-400 font-medium">Device Theme:</span>
              <button 
                onClick={() => setSimulatorThemeMode("light")} 
                className={`px-2.5 py-0.5 rounded transition font-bold ${simulatorThemeMode === "light" ? "bg-emerald-400/20 text-emerald-400 border border-emerald-500/30" : "text-slate-500"}`}
              >
                Light
              </button>
              <button 
                onClick={() => setSimulatorThemeMode("dark")} 
                className={`px-2.5 py-0.5 rounded transition font-bold ${simulatorThemeMode === "dark" ? "bg-emerald-400/20 text-emerald-400 border border-emerald-500/30" : "text-slate-500"}`}
              >
                Dark
              </button>
            </div>
            
            <div className="text-slate-500 font-mono text-[10px]">
              SCREEN: {currentScreen.toUpperCase()}
            </div>
          </div>

          {/* Android Pixel Phone Housing */}
          <div className="relative w-[370px] h-[730px] rounded-[48px] border-[10px] border-slate-800 bg-slate-950 shadow-[0_20px_50px_-15px_rgba(0,0,0,0.9)] overflow-hidden shrink-0 flex flex-col outline outline-1 outline-slate-700">
            
            {/* Phone Speaker Notch & Camera Dot */}
            <div className="absolute top-0 inset-x-0 h-6 flex items-center justify-center z-40 pointer-events-none">
              <div className="w-24 h-4.5 bg-slate-800 rounded-b-xl flex items-center justify-center gap-1.5">
                <div className="w-1.5 h-1.5 rounded-full bg-slate-900"></div>
                <div className="w-8 h-0.5 bg-slate-900 rounded-full"></div>
              </div>
            </div>

            {/* Android Status Bar */}
            <div className={`pt-5 px-6 pb-1.5 flex items-center justify-between text-[10px] font-mono select-none z-30 shrink-0 ${
              simulatorThemeMode === "light" ? "bg-[#F3EFE9] text-slate-700" : "bg-[#262625] text-slate-300"
            }`}>
              <span>09:39</span>
              <div className="flex items-center gap-1.5">
                <Wifi className="h-2.5 w-2.5 text-emerald-500" />
                <span className="text-[8px] font-black">LOCAL_DB</span>
                <Battery className="h-3 w-3" />
              </div>
            </div>

            {/* PHYSICAL APP SCREEN VIEWPORT */}
            <div className={`flex-1 flex flex-col overflow-y-auto relative ${
              simulatorThemeMode === "light" ? "bg-[#F3EFE9] text-[#262626]" : "bg-[#262626] text-[#F3EFE9]"
            }`}>
              
              {/* SCREEN 1: SPLASH SCREEN */}
              {currentScreen === "splash" && (
                <div className="flex-1 flex flex-col items-center justify-center p-6 text-center animate-fade-in bg-[#262625] text-slate-100">
                  <div className="relative mb-4">
                    <Palette className="h-14 w-14 text-emerald-400 animate-pulse" />
                    <Sparkles className="absolute -top-1 -right-1 h-5 w-5 text-amber-500 animate-bounce" />
                  </div>
                  <h1 className="text-3xl font-extrabold tracking-tight text-emerald-400">StyleAI</h1>
                  <p className="mt-1 text-xs font-mono tracking-widest uppercase text-slate-400">{s.splashSubtitle}</p>
                  
                  <button 
                    onClick={() => setCurrentScreen("onboarding")}
                    className="absolute bottom-10 bg-slate-800/80 text-white text-[10px] font-mono px-3 py-1 rounded-full border border-slate-700 hover:bg-slate-700"
                  >
                    Skip Splash Timer
                  </button>
                </div>
              )}

              {/* SCREEN 2: ONBOARDING PAGES */}
              {currentScreen === "onboarding" && (
                <div className="flex-1 p-5 flex flex-col justify-between animate-fade-in">
                  <div className="text-right">
                    <button 
                      onClick={() => setCurrentScreen("consent")}
                      className="text-[10px] font-semibold text-slate-400 hover:text-emerald-500"
                    >
                      {s.skipOnboarding}
                    </button>
                  </div>

                  <div className="my-auto px-2 text-center">
                    {onboardingSlide === 0 && (
                      <div className="animate-fade-in">
                        <div className="w-16 h-16 bg-emerald-500/15 rounded-full flex items-center justify-center mx-auto mb-5">
                          <Palette className="h-8 w-8 text-emerald-500" />
                        </div>
                        <h2 className="text-lg font-bold tracking-tight">{s.onboardingTitle1}</h2>
                        <p className="mt-2.5 text-xs text-slate-500 leading-relaxed">{s.onboardingDesc1}</p>
                      </div>
                    )}
                    {onboardingSlide === 1 && (
                      <div className="animate-fade-in">
                        <div className="w-16 h-16 bg-emerald-500/15 rounded-full flex items-center justify-center mx-auto mb-5">
                          <CheckSquare className="h-8 w-8 text-emerald-500" />
                        </div>
                        <h2 className="text-lg font-bold tracking-tight">{s.onboardingTitle2}</h2>
                        <p className="mt-2.5 text-xs text-slate-500 leading-relaxed">{s.onboardingDesc2}</p>
                      </div>
                    )}
                    {onboardingSlide === 2 && (
                      <div className="animate-fade-in">
                        <div className="w-16 h-16 bg-emerald-500/15 rounded-full flex items-center justify-center mx-auto mb-5">
                          <ShieldCheck className="h-8 w-8 text-emerald-500" />
                        </div>
                        <h2 className="text-lg font-bold tracking-tight">{s.onboardingTitle3}</h2>
                        <p className="mt-2.5 text-xs text-slate-500 leading-relaxed">{s.onboardingDesc3}</p>
                      </div>
                    )}
                  </div>

                  <div className="space-y-3">
                    <div className="flex justify-center gap-1.5">
                      {[0, 1, 2].map(idx => (
                        <div 
                          key={idx} 
                          className={`h-1.5 rounded-full transition-all duration-300 ${onboardingSlide === idx ? "w-5 bg-emerald-500" : "w-1.5 bg-slate-300"}`}
                        ></div>
                      ))}
                    </div>

                    <button
                      onClick={() => {
                        if (onboardingSlide < 2) {
                          setOnboardingSlide(prev => prev + 1);
                        } else {
                          setCurrentScreen("consent");
                        }
                      }}
                      className="w-full bg-emerald-500 hover:bg-emerald-600 text-slate-950 font-black py-3 rounded-xl text-xs tracking-wider uppercase transition-all flex items-center justify-center gap-1"
                    >
                      {onboardingSlide === 2 ? s.getStarted : s.continue}
                      <ArrowRight className="h-3.5 w-3.5" />
                    </button>
                  </div>
                </div>
              )}

              {/* SCREEN 3: PRIVACY CONSENT GATE */}
              {currentScreen === "consent" && (
                <div className="flex-1 p-5 flex flex-col justify-between animate-fade-in">
                  <div className="space-y-4">
                    <div>
                      <h2 className="text-lg font-extrabold text-emerald-600">{s.privacyConsent}</h2>
                      <p className="text-xs text-slate-400 mt-0.5">{s.consentSubtitle}</p>
                    </div>

                    <div className="space-y-3">
                      <label className="flex items-start gap-2.5 p-3 rounded-lg border border-slate-300/60 bg-white/40 cursor-pointer shadow-sm select-none">
                        <input 
                          type="checkbox" 
                          checked={consentState.permission}
                          onChange={(e) => setConsentState(prev => ({ ...prev, permission: e.target.checked }))}
                          className="mt-0.5 accent-emerald-500" 
                        />
                        <span className="text-[11px] text-slate-600 leading-normal">{s.consentCheck1}</span>
                      </label>

                      <label className="flex items-start gap-2.5 p-3 rounded-lg border border-slate-300/60 bg-white/40 cursor-pointer shadow-sm select-none">
                        <input 
                          type="checkbox" 
                          checked={consentState.disclaimer}
                          onChange={(e) => setConsentState(prev => ({ ...prev, disclaimer: e.target.checked }))}
                          className="mt-0.5 accent-emerald-500" 
                        />
                        <span className="text-[11px] text-slate-600 leading-normal">{s.consentCheck2}</span>
                      </label>

                      <label className="flex items-start gap-2.5 p-3 rounded-lg border border-slate-300/60 bg-white/40 cursor-pointer shadow-sm select-none">
                        <input 
                          type="checkbox" 
                          checked={consentState.noStore}
                          onChange={(e) => setConsentState(prev => ({ ...prev, noStore: e.target.checked }))}
                          className="mt-0.5 accent-emerald-500" 
                        />
                        <span className="text-[11px] text-slate-600 leading-normal font-medium">{s.consentCheck3}</span>
                      </label>
                    </div>

                    <div className="bg-emerald-500/10 border border-emerald-500/20 p-2.5 rounded-lg flex items-start gap-2">
                      <Lock className="h-4 w-4 text-emerald-500 shrink-0 mt-0.5" />
                      <span className="text-[10px] text-emerald-600 leading-snug font-medium">
                        {appLang === "EN" ? "GDPR COMPLIANCE: Raw photos are never stored. Exif data is stripped. Purely in-memory session." : "СОГЛАСИЕ GDPR: Исходные фото не хранятся. EXIF-данные стираются. Сессия хранится в памяти."}
                      </span>
                    </div>
                  </div>

                  <button
                    disabled={!allConsented}
                    onClick={() => setCurrentScreen("upload")}
                    className="w-full bg-emerald-500 text-slate-950 disabled:bg-slate-300 disabled:text-slate-500 font-bold py-3 rounded-xl text-xs tracking-wider uppercase transition-all"
                  >
                    {s.confirmAndContinue}
                  </button>
                </div>
              )}

              {/* SCREEN 4: PHOTO UPLOADS */}
              {currentScreen === "upload" && (
                <div className="flex-1 p-5 flex flex-col justify-between animate-fade-in">
                  <div className="space-y-4 text-slate-800">
                    <div>
                      <h2 className="text-md font-extrabold text-[#8D5B4C]">{s.uploadTitle}</h2>
                      <p className="text-[10px] text-slate-400 mt-0.5">{s.uploadSubtitle}</p>
                    </div>

                    {/* Selfie slot selector */}
                    <div className="space-y-2">
                      <span className="text-[11px] font-bold text-slate-500 block">{s.selfieLabel}</span>
                      {selfiePhoto ? (
                        <div className="bg-white border border-slate-200 p-2.5 rounded-xl flex items-center justify-between shadow-sm">
                          <div className="flex items-center gap-2">
                            <Camera className="h-4 w-4 text-slate-400" />
                            <span className="text-[10px] text-slate-600 font-semibold">{selfiePhoto.uri}</span>
                          </div>
                          <button onClick={() => setSelfiePhoto(null)} className="text-rose-600 text-[10px] font-bold">Clear</button>
                        </div>
                      ) : (
                        <div className="flex gap-2">
                          <button 
                            onClick={() => handleSelectPhoto("selfie", "safe")}
                            className="flex-1 bg-white border border-slate-200 hover:border-emerald-500 p-2 rounded-lg text-[10px] font-medium transition shadow-sm"
                          >
                            ✓ Clean Portrait
                          </button>
                          <button 
                            onClick={() => handleSelectPhoto("selfie", "blurry")}
                            className="bg-amber-50 hover:bg-amber-100 p-2 rounded-lg text-[10px] font-medium transition border border-amber-200"
                          >
                            Blurry Light
                          </button>
                          <button 
                            onClick={() => handleSelectPhoto("selfie", "restricted")}
                            className="bg-rose-50 hover:bg-rose-100 p-2 rounded-lg text-[10px] font-medium transition border border-rose-100 text-rose-700"
                          >
                            Unsafe
                          </button>
                        </div>
                      )}
                    </div>

                    {/* Body slot selector */}
                    <div className="space-y-2">
                      <span className="text-[11px] font-bold text-slate-500 block">{s.fullBodyLabel}</span>
                      {bodyPhoto ? (
                        <div className="bg-white border border-slate-200 p-2.5 rounded-xl flex items-center justify-between shadow-sm">
                          <div className="flex items-center gap-2">
                            <Upload className="h-4 w-4 text-slate-400" />
                            <span className="text-[10px] text-slate-600 font-semibold">{bodyPhoto.uri}</span>
                          </div>
                          <button onClick={() => setBodyPhoto(null)} className="text-rose-600 text-[10px] font-bold">Clear</button>
                        </div>
                      ) : (
                        <div className="flex gap-2">
                          <button 
                            onClick={() => handleSelectPhoto("body", "safe")}
                            className="flex-1 bg-white border border-slate-200 hover:border-emerald-500 p-2 rounded-lg text-[10px] font-medium transition shadow-sm"
                          >
                            ✓ Structured Stand
                          </button>
                          <button 
                            onClick={() => handleSelectPhoto("body", "blurry")}
                            className="bg-amber-50 hover:bg-amber-100 p-2 rounded-lg text-[10px] font-medium transition border border-amber-200"
                          >
                            Shaky Focus
                          </button>
                          <button 
                            onClick={() => handleSelectPhoto("body", "restricted")}
                            className="bg-rose-50 hover:bg-rose-100 p-2 rounded-lg text-[10px] font-medium transition border border-rose-100 text-rose-700"
                          >
                            Banned
                          </button>
                        </div>
                      )}
                    </div>

                    {/* Feedback warnings or blocks */}
                    {photoValidationError && (
                      <div className="bg-rose-50 border border-rose-200 text-rose-900 rounded-lg p-3 text-[10px] flex items-start gap-1.5 leading-snug shadow-sm">
                        <AlertTriangle className="h-4 w-4 text-rose-600 shrink-0" />
                        <span>{photoValidationError}</span>
                      </div>
                    )}

                    {photoValidationWarnings.length > 0 && (
                      <div className="bg-amber-50 border border-amber-250 text-amber-900 rounded-lg p-3 text-[10px] flex items-start gap-1.5 leading-snug">
                        <Info className="h-4 w-4 text-amber-600 shrink-0" />
                        <span>{photoValidationWarnings[0]}</span>
                      </div>
                    )}
                  </div>

                  <button
                    disabled={!selfiePhoto || !bodyPhoto || !!photoValidationError}
                    onClick={runAnalysisSimulation}
                    className="w-full bg-[#8D5B4C] hover:bg-[#8D5B4C]/90 text-white disabled:bg-slate-350 disabled:text-slate-500 font-bold py-3.5 rounded-xl text-xs tracking-wider uppercase transition-all shadow-md"
                  >
                    {s.startAnalysis}
                  </button>
                </div>
              )}

              {/* SCREEN 5: ANALYSIS STEPPER LOADING */}
              {currentScreen === "analysis" && (
                <div className="flex-1 p-6 flex flex-col justify-center items-center bg-[#262625] text-white text-center">
                  <div className="relative mb-6">
                    <RefreshCw className="h-12 w-12 text-emerald-400 animate-spin" />
                  </div>
                  <h3 className="text-md font-bold tracking-tight">
                    {appLang === "EN" ? "Executing Local Analysis" : "Выполнение локального анализа"}
                  </h3>
                  
                  {/* Progress bar */}
                  <div className="w-full bg-slate-800 rounded-full h-1.5 mt-4 max-w-[200px]">
                    <div 
                      className="bg-emerald-500 h-1.5 rounded-full transition-all duration-300"
                      style={{ width: `${analysisProgress}%` }}
                    ></div>
                  </div>

                  <p className="mt-4 text-[10px] text-slate-400 font-mono tracking-wide px-3 leading-relaxed">
                    {analysisStepText}
                  </p>
                </div>
              )}

              {/* SCREEN 6: STYLE REPORT CARD */}
              {currentScreen === "report" && (
                <div className="flex-1 p-4 space-y-3.5 text-slate-800 overflow-y-auto">
                  <div>
                    <span className="text-[9px] font-semibold text-slate-405 tracking-widest uppercase block">Personal Analysis Card</span>
                    <h2 className="text-md font-extrabold text-[#8D5B4C] leading-none mt-0.5">{s.reportTitle}</h2>
                  </div>

                  {/* recommended palette */}
                  <div className="bg-white border border-slate-100 p-3 rounded-xl shadow-sm text-left">
                    <strong className="text-xs text-[#8D5B4C] block mb-1">{s.colorPaletteSection}</strong>
                    <p className="text-[10px] text-slate-400 leading-normal mb-2">Soft Autumn: featuring rich moss and warm terracotta notes.</p>
                    <div className="grid grid-cols-6 gap-1">
                      {["#E6D5C3", "#CDB49E", "#8D5B4C", "#5C6B5E", "#3A4D39", "#A87C66"].map((hex, i) => (
                        <div key={i} style={{ backgroundColor: hex }} className="h-6 rounded shadow-inner" />
                      ))}
                    </div>
                  </div>

                  {/* silhouettes */}
                  <div className="bg-white border border-slate-100 p-3 rounded-xl shadow-sm space-y-1.5 text-left">
                    <strong className="text-xs text-[#8D5B4C] block">{s.silhouettesSection}</strong>
                    <div className="space-y-1 text-[10px] text-slate-500">
                      <div>• <span className="font-bold text-slate-700">High-Waisted Trousers:</span> Creates visual vertical flow.</div>
                      <div>• <span className="font-bold text-slate-700">Structured Blazers:</span> Establishes balanced shoulder structure.</div>
                    </div>
                  </div>

                  {/* style directions */}
                  <div className="bg-white border border-slate-100 p-3 rounded-xl shadow-sm space-y-1.5 text-left">
                    <strong className="text-xs text-[#8D5B4C] block">{s.styleDirectionsSection}</strong>
                    <div className="space-y-1 text-[10px] text-slate-500">
                      <div>• <span className="font-bold text-slate-700">Minimal Chic:</span> Premium neutral caching and clean layouts.</div>
                      <div>• <span className="font-bold text-slate-700">Soft Classic:</span> Symmetric traditional alignments.</div>
                    </div>
                  </div>

                  {/* Gaps checklist */}
                  <div className="bg-white border border-slate-100 p-3 rounded-xl shadow-sm space-y-2 text-left">
                    <strong className="text-xs text-[#8D5B4C] block">{s.wardrobeGapsSection}</strong>
                    <div className="space-y-1 text-[10px] text-slate-600">
                      <label className="flex items-center gap-2">
                        <input type="checkbox" defaultChecked className="accent-emerald-600" />
                        <span>Neutral Blazer (Camel/Oat)</span>
                      </label>
                      <label className="flex items-center gap-2">
                        <input type="checkbox" className="accent-emerald-600" />
                        <span>Cream Silk Button-Down</span>
                      </label>
                    </div>
                  </div>

                  {/* Body safe disclaimer */}
                  <div className="bg-slate-50 border border-slate-150 p-2.5 rounded-lg flex items-start gap-1.5 leading-snug">
                    <ShieldCheck className="h-4 w-4 text-emerald-600 shrink-0 mt-0.5" />
                    <span className="text-[9px] text-slate-400 font-mono">
                      {s.bodySafetyNotice}
                    </span>
                  </div>
                </div>
              )}

              {/* SCREEN 7: VISUALIZATION BOARD SCREEN WITH SELECTORS ENUM ONLY */}
              {currentScreen === "looks" && (
                <div className="flex-1 flex flex-col overflow-hidden text-slate-800 animate-fade-in scrollbar-none">
                  <div className="p-4.5 bg-white border-b border-slate-100 flex items-center justify-between sticky top-0 z-15 shadow-xs shrink-0">
                    <div>
                      <h2 className="text-sm font-extrabold text-[#8D5B4C]">{s.outfitBoard}</h2>
                      <p className="text-[9px] text-slate-405 leading-none mt-0.5">{s.generateLayoutsSafely}</p>
                    </div>

                    <button 
                      onClick={() => setCurrentScreen("paywall")}
                      className="bg-[#F9EFEA] text-[9.5px] text-[#8D5B4C] font-semibold px-2 py-0.5 rounded-md border border-[#8D5B4C]/20"
                    >
                      {creditBalance.isPro ? "★ Pro" : `${creditBalance.credits} Cr ⏵`}
                    </button>
                  </div>

                  <div className="flex-1 overflow-y-auto p-3.5 space-y-3.5 scrollbar-thin">
                    
                    {/* Banned warnings block */}
                    <div className="bg-slate-50 border border-slate-150 p-2.5 rounded-xl space-y-1">
                      <span className="text-[10px] text-slate-500 leading-relaxed font-semibold">
                        {s.visSafetyNotice}
                      </span>
                    </div>

                    {/* SELECTORS GRID (Zero free-text, preventing prompt injection) */}
                    <div className="bg-white border border-slate-100/80 p-3 rounded-2xl space-y-2.5 shadow-xs text-slate-800 text-left">
                      
                      {/* 1. Occasion */}
                      <div>
                        <span className="text-[9px] font-bold text-slate-400 uppercase tracking-wider block mb-1">1. {s.occasionLabel}</span>
                        <div className="flex gap-1 overflow-x-auto pb-0.5 select-none scrollbar-none">
                          {["Everyday", "Office", "Date", "Travel", "Event"].map(o => (
                            <button 
                              key={o}
                              onClick={() => setSelectedOccasion(o as any)}
                              className={`px-2.5 py-1 rounded text-[9.5px] border shrink-0 font-medium ${
                                selectedOccasion === o ? "bg-[#8D5B4C] text-white border-[#8D5B4C]" : "bg-slate-50 text-slate-500 border-slate-150"
                              }`}
                            >
                              {o}
                            </button>
                          ))}
                        </div>
                      </div>

                      {/* 2. Style */}
                      <div>
                        <span className="text-[9px] font-bold text-slate-400 uppercase tracking-wider block mb-1">2. {s.styleLabel}</span>
                        <div className="flex gap-1 overflow-x-auto pb-0.5 select-none scrollbar-none">
                          {["Minimal", "Classic", "Smart casual", "Feminine", "Streetwear"].map(st => (
                            <button 
                              key={st}
                              onClick={() => setSelectedStyle(st as any)}
                              className={`px-2.5 py-1 rounded text-[9.5px] border shrink-0 font-medium ${
                                selectedStyle === st ? "bg-[#8D5B4C] text-white border-[#8D5B4C]" : "bg-slate-50 text-slate-500 border-slate-150"
                              }`}
                            >
                              {st}
                            </button>
                          ))}
                        </div>
                      </div>

                      {/* 3. Season */}
                      <div>
                        <span className="text-[9px] font-bold text-slate-400 uppercase tracking-wider block mb-1">3. {s.seasonLabel}</span>
                        <div className="flex gap-1 overflow-x-auto pb-0.5 select-none scrollbar-none">
                          {["Spring", "Summer", "Autumn", "Winter"].map(se => (
                            <button 
                              key={se}
                              onClick={() => setSelectedSeason(se as any)}
                              className={`px-2.5 py-1 rounded text-[9.5px] border shrink-0 font-medium ${
                                selectedSeason === se ? "bg-[#8D5B4C] text-white border-[#8D5B4C]" : "bg-slate-50 text-slate-500 border-slate-150"
                              }`}
                            >
                              {se}
                            </button>
                          ))}
                        </div>
                      </div>

                      {/* 4. Formality */}
                      <div>
                        <span className="text-[9px] font-bold text-slate-400 uppercase tracking-wider block mb-1">4. {s.formalityLabel}</span>
                        <div className="flex gap-1 overflow-x-auto pb-0.5 select-none scrollbar-none">
                          {["Casual", "Polished", "Formal"].map(f => (
                            <button 
                              key={f}
                              onClick={() => setSelectedFormality(f as any)}
                              className={`px-2.5 py-1 rounded text-[9.5px] border shrink-0 font-medium ${
                                selectedFormality === f ? "bg-[#8D5B4C] text-white border-[#8D5B4C]" : "bg-slate-50 text-slate-500 border-slate-150"
                              }`}
                            >
                              {f}
                            </button>
                          ))}
                        </div>
                      </div>

                      {/* 5. Color Direction */}
                      <div>
                        <span className="text-[9px] font-bold text-slate-400 uppercase tracking-wider block mb-1">5. {s.colorDirectionLabel}</span>
                        <div className="flex gap-1 overflow-x-auto pb-0.5 select-none scrollbar-none">
                          {["Neutral", "Warm", "Cool", "Soft", "Contrast"].map(cd => (
                            <button 
                              key={cd}
                              onClick={() => setSelectedColorDir(cd as any)}
                              className={`px-2.5 py-1 rounded text-[9.5px] border shrink-0 font-medium ${
                                selectedColorDir === cd ? "bg-[#8D5B4C] text-white border-[#8D5B4C]" : "bg-slate-50 text-slate-500 border-slate-150"
                              }`}
                            >
                              {cd}
                            </button>
                          ))}
                        </div>
                      </div>

                      <button 
                        onClick={triggerSafeVisualization}
                        className="w-full bg-[#8D5B4C] hover:bg-[#8D5B4C]/90 text-white font-black py-2.5 rounded-xl text-xs tracking-wider transition-all shadow-xs block mt-3"
                      >
                        {s.renderOutfit}
                      </button>

                      {generationSuccessAlert && (
                        <div className="bg-emerald-50 border border-emerald-200 text-emerald-800 rounded-lg p-2 text-[9.5px] text-center font-bold">
                          ✓ {appLang === "EN" ? "Outfit reference generated successfully! 1 Credit spent." : "Образ сгенерирован успешно! Списан 1 кредит."}
                        </div>
                      )}
                    </div>

                    {/* Outfit Ideas Grid Lists */}
                    <div className="grid grid-cols-2 gap-2 text-left">
                      {outfits
                        .map(outfit => (
                          <div key={outfit.id} className="bg-white border border-slate-205 rounded-xl overflow-hidden shadow-xs flex flex-col justify-between">
                            <div 
                              style={{ backgroundColor: outfit.colors[0] }} 
                              className="h-12 relative flex items-center justify-center border-b border-slate-100 shadow-inner"
                            >
                              <span className="text-[8px] font-bold text-white bg-slate-900/40 px-2 py-0.5 rounded-full">
                                {outfit.occasion}
                              </span>
                            </div>

                            <div className="p-2 space-y-1 flex-1 flex flex-col justify-between">
                              <div>
                                <h4 className="text-[10px] font-bold text-slate-800 leading-tight line-clamp-1">{outfit.title}</h4>
                                <div className="space-y-0.5 mt-1">
                                  {outfit.items.slice(0, 2).map((itm, i) => (
                                    <span key={i} className="text-[8.5px] text-slate-400 block truncate">• {itm}</span>
                                  ))}
                                </div>
                              </div>

                              <div className="flex items-center justify-between pt-1 border-t border-slate-100">
                                <span className="text-[8.5px] text-[#8D5B4C] font-semibold">{outfit.style}</span>
                                <button 
                                  onClick={() => toggleSaveOutfit(outfit.id)}
                                  className={`text-[8.5px] px-2 py-0.5 rounded font-bold border transition ${
                                    outfit.isSaved 
                                      ? "bg-emerald-500 text-white border-emerald-500" 
                                      : "bg-slate-50 text-slate-600 border-slate-201 hover:bg-slate-100"
                                  }`}
                                >
                                  {outfit.isSaved ? "Saved" : "Save"}
                                </button>
                              </div>
                            </div>
                          </div>
                        ))}
                    </div>
                  </div>
                </div>
              )}

              {/* SCREEN 8: PAYWALL */}
              {currentScreen === "paywall" && (
                <div className="flex-1 p-5 flex flex-col justify-between overflow-y-auto animate-fade-in text-slate-800">
                  <div className="space-y-3.5 text-left">
                    <div className="text-center">
                      <h2 className="text-lg font-extrabold text-[#8D5B4C]">{s.unlockPremium}</h2>
                      <p className="text-[10px] text-slate-400 mt-0.5">{s.unlockSubtitle}</p>
                    </div>

                    <div 
                      onClick={() => {
                        setCreditBalance({ credits: 20, isPro: true });
                        setSettingsFeedback("Plan Complete Style Report credited.");
                        setCurrentScreen("looks");
                      }}
                      className="border border-[#8D5B4C]/30 bg-[#F9EFEA]/30 p-3 rounded-xl cursor-pointer hover:border-[#8D5B4C]"
                    >
                      <div className="flex justify-between items-center text-xs">
                        <strong className="text-[#8D5B4C]">{s.paywallCompleteReport}</strong>
                        <span className="text-xs font-black text-[#8D5B4C]">$9.99</span>
                      </div>
                    </div>

                    <div 
                      onClick={() => {
                        setCreditBalance(prev => ({ ...prev, isPro: true }));
                        setSettingsFeedback("Pro Subscription credited.");
                        setCurrentScreen("looks");
                      }}
                      className="border border-slate-205 bg-white p-3 rounded-xl cursor-pointer hover:border-emerald-500"
                    >
                      <div className="flex justify-between items-center text-xs">
                        <strong className="text-emerald-800">{s.paywallSubPlan}</strong>
                        <span className="text-xs font-black text-emerald-800">$9.99/mo</span>
                      </div>
                    </div>

                    <div 
                      onClick={() => {
                        setCreditBalance(prev => ({ ...prev, credits: prev.credits + 10 }));
                        setSettingsFeedback("10 extra Outfit credits added.");
                        setCurrentScreen("looks");
                      }}
                      className="border border-slate-205 bg-white p-3 rounded-xl cursor-pointer hover:border-emerald-500"
                    >
                      <div className="flex justify-between items-center text-xs">
                        <strong className="text-slate-800">{s.paywallCreditsPack}</strong>
                        <span className="text-xs font-black text-slate-800">$4.99</span>
                      </div>
                    </div>
                  </div>

                  <button 
                    onClick={() => setCurrentScreen("looks")}
                    className="w-full bg-slate-200 text-slate-600 font-bold py-2 rounded-xl text-xs uppercase"
                  >
                    {s.goBackToOutfits}
                  </button>
                </div>
              )}

              {/* SCREEN 9: HISTORY SCREEN */}
              {currentScreen === "history" && (
                <div className="flex-1 p-5 flex flex-col justify-between overflow-y-auto animate-fade-in text-slate-800 text-left">
                  <div className="space-y-4">
                    <div>
                      <h2 className="text-md font-extrabold text-[#8D5B4C]">{s.historyTitle}</h2>
                      <p className="text-[10px] text-slate-400 mt-0.5">{s.historySubtitle}</p>
                    </div>

                    <div className="space-y-2">
                      <span className="text-[9px] font-bold text-slate-400 uppercase tracking-widest block">{s.activeReports}</span>
                      {hasGeneratedReport ? (
                        <div className="bg-white border border-slate-205 p-3 rounded-xl shadow-xs text-slate-700 space-y-2">
                          <div className="flex justify-between items-start text-xs">
                            <div>
                              <strong className="text-[#8D5B4C]">{appLang === "EN" ? "Soft Autumn Report" : "Отчет: Мягкая Осень"}</strong>
                              <span className="text-[9px] text-slate-400 block font-mono">Size matches: 4 item counts</span>
                            </div>
                            <button 
                              onClick={() => setCurrentScreen("report")}
                              className="bg-slate-100 text-slate-600 hover:bg-slate-200 font-bold px-2 py-1 rounded text-[9px]"
                            >
                              Load
                            </button>
                          </div>
                        </div>
                      ) : (
                        <div className="bg-slate-100 border border-slate-200 border-dashed p-4 rounded-xl text-center text-[10px] text-slate-400 leading-snug">
                          {s.emptyHistory}
                        </div>
                      )}
                    </div>

                    {/* Saved Looks List */}
                    <div className="space-y-2">
                      <span className="text-[9px] font-bold text-slate-400 uppercase tracking-widest block">{s.savedLooks} ({savedLooks.length})</span>
                      {savedLooks.length > 0 ? (
                        <div className="space-y-1.5 max-h-[190px] overflow-y-auto pr-1">
                          {savedLooks.map(look => (
                            <div key={look.id} className="bg-white border border-slate-150 p-2 rounded-xl flex items-center justify-between text-slate-700 shadow-xs">
                              <div className="flex items-center gap-2">
                                <div style={{ backgroundColor: look.color }} className="w-5.5 h-5.5 rounded" />
                                <span className="text-[10px] font-semibold truncate max-w-[150px]">{look.title}</span>
                              </div>
                              <button 
                                onClick={() => {
                                  setSavedLooks(prev => prev.filter(l => l.id !== look.id));
                                  setOutfits(prev => prev.map(o => o.id === look.id.replace("look_", "") ? { ...o, isSaved: false } : o));
                                }}
                                className="text-rose-600 text-[10px] font-bold hover:underline"
                              >
                                Delete
                              </button>
                            </div>
                          ))}
                        </div>
                      ) : (
                        <div className="bg-slate-100 border border-slate-200 border-dashed p-4 rounded-xl text-center text-[10px] text-slate-400 leading-snug">
                          {appLang === "EN" ? "No saved outfit profiles yet." : "Сохраненные образы отсутствуют."}
                        </div>
                      )}
                    </div>
                  </div>
                </div>
              )}

              {/* SCREEN 10: SETTINGS SCREEN */}
              {currentScreen === "settings" && (
                <div className="flex-1 p-5 flex flex-col justify-between overflow-y-auto animate-fade-in text-slate-800 text-left">
                  <div className="space-y-4">
                    <div>
                      <h2 className="text-md font-extrabold text-[#8D5B4C]">{s.appSettings}</h2>
                      <p className="text-[10px] text-slate-400 mt-0.5">{s.settingsSubtitle}</p>
                    </div>

                    {settingsFeedback && (
                      <div className="bg-emerald-50 border border-emerald-250 text-emerald-800 rounded-lg p-2 text-[9.5px] text-center font-bold">
                        {settingsFeedback}
                      </div>
                    )}

                    {/* Choose Language */}
                    <div className="bg-white rounded-xl border border-slate-150 p-3 space-y-2">
                      <span className="text-[9px] font-bold text-slate-450 uppercase tracking-wider block">{s.settingsLanguageSection}</span>
                      <div className="flex gap-2">
                        <button 
                          onClick={() => setAppLang("EN")}
                          className={`flex-1 font-bold py-1.5 text-xs rounded transition ${appLang === "EN" ? "bg-emerald-500 text-slate-950" : "bg-slate-150 text-slate-600"}`}
                        >
                          English
                        </button>
                        <button 
                          onClick={() => setAppLang("RU")}
                          className={`flex-1 font-bold py-1.5 text-xs rounded transition ${appLang === "RU" ? "bg-emerald-500 text-slate-950" : "bg-slate-150 text-slate-600"}`}
                        >
                          Русский
                        </button>
                      </div>
                    </div>

                    <div className="bg-white rounded-xl border border-slate-150 p-3 space-y-2.5">
                      <div className="space-y-0.5">
                        <strong className="text-xs text-slate-700 block">{appLang === "EN" ? "GDPR Compliance Rules" : "Защита данных по GDPR"}</strong>
                        <p className="text-[9.5px] text-slate-500 leading-normal">
                          {appLang === "EN" 
                            ? "All user-captured silhouettes, colors, and shape variables exist exclusively in volatile GPU buffers." 
                            : "Все пользовательские силуэты, параметры цвета и формы хранятся исключительно во временной памяти."}
                        </p>
                      </div>
                    </div>

                    {/* Reset options with confirmations */}
                    <div className="space-y-2">
                      <button 
                        onClick={() => {
                          setSettingsFeedback("Style data exported: StyleReport-SoftAutumn-Export.json");
                          setTimeout(() => setSettingsFeedback(null), 3000);
                        }}
                        className="w-full bg-[#8D5B4C] hover:bg-[#8D5B4C]/90 text-white font-bold py-2 rounded-lg text-[10px] flex items-center justify-center gap-1 shadow-xs"
                      >
                        <Download className="h-3.5 w-3.5" />
                        {s.settingsExportButton}
                      </button>

                      {/* 1. Clear style data button */}
                      <button 
                        onClick={() => setDialogOpen("styleData")}
                        className="w-full bg-slate-100 hover:bg-slate-200 text-slate-700 font-bold py-2 rounded-lg text-[10px]"
                      >
                        {s.settingsBtnWipeLooks}
                      </button>

                      {/* 2. Reset app onboarding button */}
                      <button 
                        onClick={() => setDialogOpen("onboarding")}
                        className="w-full bg-rose-50 hover:bg-rose-100 text-rose-800 font-bold py-2 rounded-lg text-[10px]"
                      >
                        {s.settingsBtnResetOnboarding}
                      </button>
                    </div>

                    {/* Hidden Developer Checklist categories */}
                    {devReadinessVisible && (
                      <div className="bg-slate-900 text-slate-100 p-3 rounded-xl border border-slate-800 text-left space-y-2 mt-4 select-text">
                        <strong className="text-xs text-amber-400 block">🛠️ Developer MVP Readiness Checklist</strong>
                        
                        <div className="text-[9.5px] text-slate-400 space-y-1">
                          <div className="font-bold text-slate-350">1. Native Android:</div>
                          <div>✓ Jetpack Compose screen counterparts for all screens</div>
                          <div>✓ ViewModel state flows mapped securely</div>
                          <div>✓ Decoupled clean StyleRepository implementations</div>

                          <div className="font-bold text-slate-355 mt-1">2. AI Safety:</div>
                          <div>✓ Forced user-consent disclaimers enabled</div>
                          <div>✓ Free-text prompt removed; controlled enums only</div>
                          <div>✓ Banned words checked at initialization model level</div>

                          <div className="font-bold text-slate-355 mt-1">3. GDPR & Privacy:</div>
                          <div>✓ EXIF metadata stripper code defined</div>
                          <div>✓ Raw photo cache wipes automatically on complete</div>
                          <div>✓ Zero plain file logs (using SafeLogger)</div>
                        </div>

                        <button 
                          onClick={() => {
                            setVersionTapCount(0);
                            setDevReadinessVisible(false);
                          }}
                          className="bg-slate-800 text-slate-400 text-[9px] px-2 py-0.5 rounded"
                        >
                          Hide Dev Mode
                        </button>
                      </div>
                    )}
                  </div>

                  {/* Footer clickable version tapper */}
                  <div className="text-center space-y-0.5 pt-4">
                    <span 
                      onClick={handleVersionTapped}
                      className="text-[10px] text-slate-400 block cursor-pointer select-none"
                    >
                      App Version: 1.0.0-MVP (Build-26 - Tap {5 - Math.min(5, versionTapCount)} times for Dev Checklist)
                    </span>
                    <span className="text-[9px] text-slate-400 block">StyleAI Studio Copyright © 2026</span>
                  </div>
                </div>
              )}

            </div>

            {/* ANDROID DEVICE BOTTOM NAV BAR STRIP */}
            {hasGeneratedReport && (
              <div className={`py-2 px-4 shadow-2xl border-t select-none z-30 shrink-0 ${
                simulatorThemeMode === "light" 
                  ? "bg-white text-slate-600 border-slate-200" 
                  : "bg-[#1f1f1f] text-slate-300 border-slate-800"
              }`}>
                <div className="flex justify-around items-center">
                  <button 
                    onClick={() => setCurrentScreen("report")}
                    className={`flex flex-col items-center gap-0.5 px-3 py-1 text-slate-400 transition ${currentScreen === "report" && "text-[#8D5B4C]"}`}
                  >
                    <Palette className="h-4 w-4" />
                    <span className="text-[8.5px] font-bold">Report</span>
                  </button>

                  <button 
                    onClick={() => setCurrentScreen("looks")}
                    className={`flex flex-col items-center gap-0.5 px-3 py-1 text-slate-400 transition ${currentScreen === "looks" && "text-[#8D5B4C]"}`}
                  >
                    <Sparkles className="h-4 w-4" />
                    <span className="text-[8.5px] font-bold">Looks</span>
                  </button>

                  <button 
                    onClick={() => setCurrentScreen("history")}
                    className={`flex flex-col items-center gap-0.5 px-3 py-1 text-slate-400 transition ${currentScreen === "history" && "text-[#8D5B4C]"}`}
                  >
                    <History className="h-4 w-4" />
                    <span className="text-[8.5px] font-bold">History</span>
                  </button>

                  <button 
                    onClick={() => setCurrentScreen("settings")}
                    className={`flex flex-col items-center gap-0.5 px-3 py-1 text-slate-400 transition ${currentScreen === "settings" && "text-[#8D5B4C]"}`}
                  >
                    <Settings className="h-4 w-4" />
                    <span className="text-[8.5px] font-bold">Settings</span>
                  </button>
                </div>
              </div>
            )}

            {/* Android Screen System Pill Accent */}
            <div className={`pt-1 pb-2 flex items-center justify-center shrink-0 z-30 ${
              simulatorThemeMode === "light" ? "bg-white" : "bg-[#1f1f1f]"
            }`}>
              <div className="w-24 h-0.5 bg-slate-450 rounded-full"></div>
            </div>

          </div>
        </section>

        {/* RIGHT COLUMN: Senior Kotlin Code Inspector Panels */}
        <section className="w-full lg:w-[460px] xl:w-[500px] bg-slate-900 overflow-y-auto flex flex-col border-t lg:border-t-0 border-slate-800 shrink-0 select-none scrollbar-thin">
          <div className="p-4 border-b border-slate-800 bg-slate-950 flex flex-col gap-2.5">
            <div className="flex items-center gap-1.5">
              <BookOpen className="h-5 w-5 text-emerald-400" />
              <h3 className="font-bold text-sm text-slate-200">Jetpack Compose Architecture</h3>
            </div>
            <p className="text-xs text-slate-400 leading-relaxed text-left">
              Explore the exact Kotlin files currently running inside the native Android codebase. The active tab updates as you navigate screens on the simulator.
            </p>
          </div>

          {/* Module Selector tabs */}
          <div className="bg-slate-950/40 p-2 flex gap-1.5 overflow-x-auto border-b border-slate-800 select-none scrollbar-none">
            {Object.keys(kotlinFiles).map((key) => {
              const file = kotlinFiles[key];
              const isActive = activeCodeKey === key;
              return (
                <button
                  key={key}
                  onClick={() => setActiveCodeKey(key)}
                  className={`px-3 py-1.5 rounded-lg text-xs font-mono whitespace-nowrap border transition ${
                    isActive 
                      ? "bg-slate-800 text-emerald-400 border-slate-700 shadow-inner" 
                      : "bg-slate-900/60 text-slate-500 border-slate-800 hover:bg-slate-850 hover:text-slate-400"
                  }`}
                >
                  {file.name}
                </button>
              );
            })}
          </div>

          {/* Active File Metadata */}
          <div className="p-4 bg-slate-850 border-b border-slate-850 flex flex-col gap-1 text-left">
            <div className="flex items-center gap-2">
              <span className="text-[10px] bg-slate-950 font-bold px-2.5 py-1 rounded text-slate-400 font-mono tracking-wider">
                /{kotlinFiles[activeCodeKey]?.path || ""}
              </span>
            </div>
            <p className="text-[11px] text-slate-350 leading-relaxed italic">
              ↳ {kotlinFiles[activeCodeKey]?.description || ""}
            </p>
          </div>

          {/* Code block viewer */}
          <div className="flex-1 p-4 bg-slate-950 font-mono text-[10.5px] text-slate-200 overflow-x-auto whitespace-pre select-text selection:bg-emerald-500 selection:text-slate-950 scrollbar-thin text-left">
            {kotlinFiles[activeCodeKey]?.code || ""}
          </div>

          {/* Architecture Checklist Footers */}
          <div className="p-4 bg-slate-950 border-t border-slate-850 space-y-3 shrink-0 text-left">
            <span className="text-[10px] font-bold text-slate-400 tracking-widest uppercase block mb-1">Architecture Compliance Matrix</span>
            
            <div className="grid grid-cols-2 gap-2 text-[10px] text-slate-500">
              <div className="flex items-center gap-1.5">
                <CheckCircle className="h-3.5 w-3.5 text-emerald-400 shrink-0" />
                <span>Type-Safe Selection Request</span>
              </div>
              <div className="flex items-center gap-1.5">
                <CheckCircle className="h-3.5 w-3.5 text-emerald-400 shrink-0" />
                <span>Exif Metadata Stripping</span>
              </div>
              <div className="flex items-center gap-1.5">
                <CheckCircle className="h-3.5 w-3.5 text-emerald-400 shrink-0" />
                <span>Dynamic RU/EN Locale state</span>
              </div>
              <div className="flex items-center gap-1.5">
                <CheckCircle className="h-3.5 w-3.5 text-emerald-400 shrink-0" />
                <span>Full SafeLogger Redacting</span>
              </div>
            </div>
          </div>
        </section>

      </main>

      {/* Confirmation Dialog 1: Clear local style data only */}
      {dialogOpen === "styleData" && (
        <div className="fixed inset-0 bg-slate-950/80 backdrop-blur-sm flex items-center justify-center z-50 p-4">
          <div className="bg-slate-900 border border-slate-800 rounded-2xl max-w-sm w-full p-6 space-y-4">
            <div className="flex items-center gap-2 text-rose-400">
              <AlertTriangle className="h-5 w-5" />
              <h3 className="font-bold text-md">{s.settingsPurgePrompt}</h3>
            </div>
            <p className="text-xs text-slate-300 leading-relaxed text-left">
              {s.settingsPurgeDesc}
            </p>
            <div className="flex justify-end gap-3 pt-2">
              <button 
                onClick={() => setDialogOpen("none")}
                className="px-4 py-2 bg-slate-800 hover:bg-slate-750 text-slate-300 rounded-lg text-xs font-bold"
              >
                {appLang === "EN" ? "Cancel" : "Отмена"}
              </button>
              <button 
                onClick={() => {
                  setDialogOpen("none");
                  handleWipeStyleDataOnly();
                }}
                className="px-4 py-2 bg-rose-600 hover:bg-rose-700 text-white rounded-lg text-xs font-bold"
              >
                {s.confirmPurge}
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Confirmation Dialog 2: Reset App Onboarding */}
      {dialogOpen === "onboarding" && (
        <div className="fixed inset-0 bg-slate-950/80 backdrop-blur-sm flex items-center justify-center z-50 p-4">
          <div className="bg-slate-900 border border-slate-800 rounded-2xl max-w-sm w-full p-6 space-y-4">
            <div className="flex items-center gap-2 text-rose-400">
              <RefreshCw className="h-5 w-5" />
              <h3 className="font-bold text-md">
                {appLang === "EN" ? "Reset Onboarding Sliders?" : "Сбросить онбординг?"}
              </h3>
            </div>
            <p className="text-xs text-slate-300 leading-relaxed text-left">
              {appLang === "EN"
                ? "This will wipe your active disclaimer state consents and return you to the onboarding screens. Confirm?"
                : "Это сбросит ваши согласия и вернет вас к экранам онбординга. Подтвердить?"}
            </p>
            <div className="flex justify-end gap-3 pt-2">
              <button 
                onClick={() => setDialogOpen("none")}
                className="px-4 py-2 bg-slate-800 hover:bg-slate-750 text-slate-300 rounded-lg text-xs font-bold"
              >
                {appLang === "EN" ? "Cancel" : "Отмена"}
              </button>
              <button 
                onClick={() => {
                  setDialogOpen("none");
                  handleResetAppOnboardingOnly();
                }}
                className="px-4 py-2 bg-rose-600 hover:bg-rose-700 text-white rounded-lg text-xs font-bold"
              >
                {appLang === "EN" ? "Reset" : "Сбросить"}
              </button>
            </div>
          </div>
        </div>
      )}

    </div>
  );
}
