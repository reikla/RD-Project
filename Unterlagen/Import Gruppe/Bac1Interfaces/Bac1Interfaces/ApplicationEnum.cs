using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Bac1Interfaces
{
    public enum ApplicationEnum
    {
        /// <summary>
        /// General
        /// </summary>
        Lighting = 0,
        ElectricHeat = 1,
        SolarThermalPump = 2,
        Boiler = 3,
        GasBoiler = 4,
        AirConditioning = 5,
        Disposal = 6,
        TotalOutlets = 7,
        TotalLights = 8,
        ElectricHeater = 9,
        Aggregate = 10,

        /// <summary>
        /// Office
        /// </summary>
        OfficePC = 11,
        LCDOffice = 12,
        HifiOffice = 13,
        OfficeLamp = 14,
        OfficeFan = 15,

        /// <summary>
        /// Bedroom
        /// </summary>
        BedroomTV = 16,
        LCDBedroom24Inch = 17,
        StereoSpeakersBedroom = 18,
        BedsideLight = 19,
        BedroomLamp = 20,
        BedroomChargers = 21,

        /// <summary>
        /// Kitchen
        /// </summary>
        KitchenOutlets = 22,
        KitchenLights = 23,
        KitchenDtLamp = 24,
        KitchenLamp = 25,
        Kitchenware = 26,
        FridgeWithFreezer = 27,
        Freezer = 28,
        Refrigerator = 29,
        Dishwasher = 30,
        FumeHood = 31,
        Hob = 32,
        Oven = 33,
        GasOven = 34,
        Stove = 35,
        ElectricOven = 36,
        Furnance = 37,
        Microwave = 38,
        RiceCooker = 39,
        Cooker = 40,
        CoffeeMachine = 41,
        BreadMachine = 42,
        Toaster = 43,
        KitchenTV = 44,
        KitchenPhoneAndStereo = 45,
        KitchenRadio = 46,
        WaterKettle = 47,
        Blender = 48,

        /// <summary>
        /// Entertainment
        /// </summary>
        HTPC = 49,
        HomeTheaterAMP = 50,
        I7Desktop = 51,
        Computer = 52,
        ComputerWithScannerAndPrinter = 53,
        DesktopComputerWithScreen = 54,
        PS4 = 55,
        Playstation = 56,
        AtomPc = 57,
        VideogameConsoleAndRadio = 58,
        Projector = 59,
        Monitor = 60,
        TV = 61,
        PlasmaTV = 62,
        LCDTV = 63,
        LCD24Inch = 64,
        Notebook = 65,
        Laptop = 66,
        SkyHdBox = 67,
        TVDVDDigiboxLamp = 68,
        Speakers = 69,
        Radio = 70,
        RadioWithAmplifier = 71,

        /// <summary>
        /// Living Room
        /// </summary>
        TVWithDecoderAndComputerInLivingRoom = 72,
        LivingRoomTV = 73,
        AmpLivingroom = 74,
        LivingroomLampAndTV = 75,
        SubwooferLivingroom = 76,
        DABRadioLivingroom = 77,
        LivingroomLamp = 78,

        /// <summary>
        /// Bathroom
        /// </summary>
        Hairdryer = 79,
        Straighteners = 80,
        BathroomGFI = 81,

        /// <summary>
        /// Cleaning
        /// </summary>
        WashingMachine = 82,
        WasherDryer = 83,
        Dryier = 84,
        Iron = 85,
        SteamIron = 86,
        Hoover = 87,
        VacuumCleaner = 88,

        /// <summary>
        /// Children
        /// </summary>
        ChildsTableLamp = 89,
        ChildsLamp = 90,
        BabyMonitor = 91,

        /// <summary>
        /// Network
        /// </summary>
        NAS = 92,
        HDDServer = 93,
        Core2Server = 94,
        Server = 95,
        GigEAndUSBHub = 96,
        Router = 97,
        AdslRouter = 98,
        NetworkRouter = 99,
        Modem = 100,

        /// <summary>
        /// Electronics
        /// </summary>
        Electronics = 101,
        LEDPrinter = 102,
        LightingCircuit = 103,
        IPadCharger = 104,
        fluorescentLamp = 105,
        SamsungCharger = 106,
        DataLoggerPc = 107,
        BatteryCharger = 108,
        Lamp = 109,
        SolderingIron = 110,

        /// <summary>
        /// Outside
        /// </summary>
        EntranceOutlet = 111,
        OutdoorOutlets = 112,

        /// <summary>
        /// Combined
        /// </summary>
        WashingMaschineAndMicrowaveAndBredmaker = 113,
        KettleAndRadio = 114,

        /// <summary>
        /// Other
        /// </summary>
        Mains = 115,
        OutletsUnknown = 116,
        Miscellaeneous = 117,
        SmokeAlarms = 118,
        Treadmill = 119,
        Subpanel = 120,

        /// <summary>
        /// GREEND
        /// </summary>
        UnkknownGreendH1A9 = 121, //Abklären!
        UnkknownGreendH8A9 = 122, //Abklären!

        /// <summary>
        /// UK-DALE
        /// </summary>
        UKDALEwholeHouse1 = 123,
        UKDALEwholeHouse2 = 124,
        UKDALEwholeHouse3 = 125,
        UKDALEwholeHouse4 = 126,
        UKDALEwholeHouse5 = 127,

        None = 65,
    }
}
