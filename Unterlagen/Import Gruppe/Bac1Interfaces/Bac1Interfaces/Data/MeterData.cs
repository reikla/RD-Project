using Bac1Interfaces.Database;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Bac1Interfaces.Data
{
    class MeterData : IData
    {
        /// <summary>
        /// Data Id's
        /// </summary>
        int dataId;
        int meterId;

        /// <summary>
        /// Time of recording
        /// </summary>
        String timestamp;

        /// <summary>
        /// Total work
        /// </summary>
        double countTotal;
        double countRegister1;
        double countRegister2;
        double countRegister3;
        double countRegister4;

        /// <summary>
        /// Aufgenommene Leistung
        /// </summary>
        double powerP1; //Phase 1 - Wenn nur eine Phase gesamt
        double powerP2; //Phase 2
        double powerP3; //Phase 3

        /// <summary>
        /// Aufgenommene Energie
        /// </summary>
        double workP1; //Phase 1 - Wenn nur eine Phase gesamt
        double workP2; //Phase 2
        double workP3; //Phase 3

        /// <summary>
        /// Aufnahmefrequenz
        /// </summary>
        double frequenzy;

        /// <summary>
        /// Spannung
        /// </summary>
        double voltage;

        /// <summary>
        /// Konstruktor ohne Meter Id
        /// </summary>
        public MeterData()
        {
            DatabaseDriver driver = new DatabaseDriver();
            this.dataId = driver.GetUniqueMeterDataId();

            this.timestamp = "946684800";
        }

        /// <summary>
        /// Konstruktor mit Smart Meter Id
        /// </summary>
        /// <param name="_meterId">Fremdschlüssel zu Meter Management Tabelle</param>
        public MeterData(int _meterId)
        {
            DatabaseDriver driver = new DatabaseDriver();
            this.dataId = driver.GetUniqueMeterDataId();
            this.meterId = _meterId;
            driver.CloseConnection();

            this.timestamp = "946684800";
        }

        /// <summary>
        /// Return the data as a stringarray in order of the database
        /// </summary>
        /// <returns>Stringarray containing all the information</returns>
        override public String[] GetData()
        {
            String[] data = new String[16];

            data[0] = this.dataId.ToString();
            data[1] = this.meterId.ToString();
            data[2] = this.timestamp;
            data[3] = this.countTotal.ToString();
            data[4] = this.countRegister1.ToString();
            data[5] = this.countRegister2.ToString();
            data[6] = this.countRegister3.ToString();
            data[7] = this.countRegister4.ToString();
            data[8] = this.powerP1.ToString();
            data[9] = this.powerP2.ToString();
            data[10] = this.powerP3.ToString();
            data[11] = this.workP1.ToString();
            data[12] = this.workP2.ToString();
            data[13] = this.workP3.ToString();
            data[14] = this.frequenzy.ToString();
            data[15] = this.voltage.ToString();

            return data;
        }

        public String GetTimestamp()
        {
            return this.timestamp;
        }

        public double GetCountTotal()
        {
            return this.countTotal;
        }

        public double GetCountRegister1()
        {
            return this.countRegister1;
        }

        public double GetCountRegister2()
        {
            return this.countRegister2;
        }

        public double GetCountRegister3()
        {
            return this.countRegister3;
        }

        public double GetCountRegister4()
        {
            return this.countRegister4;
        }

        public double GetPowerP1()
        {
            return this.powerP1;
        }

        public double GetPowerP2()
        {
            return this.powerP2;
        }

        public double GetPowerP3()
        {
            return this.powerP3;
        }

        public double GetWorkP1()
        {
            return this.workP1;
        }

        public double GetWorkP2()
        {
            return this.workP2;
        }

        public double GetWorkP3()
        {
            return this.workP3;
        }

        public double GetFrequenzy()
        {
            return this.frequenzy;
        }
        
        public double GetVoltage()
        {
            return this.voltage;
        }

        public void SetTimestamp(String _timestamp)
        {
            this.timestamp = _timestamp;
        }

        public void SetCountTotal(double _countTotal)
        {
            this.countTotal = _countTotal;
        }

        public void SetCountRegister1(double _countRegister1)
        {
            this.countRegister1 = _countRegister1;
        }

        public void SetCountRegister2(double _countRegister2)
        {
            this.countRegister2 = _countRegister2;
        }

        public void SetCountRegister3(double _countRegister3)
        {
            this.countRegister3 = _countRegister3;
        }

        public void SetCountRegister4(double _countRegister4)
        {
            this.countRegister4 = _countRegister4;
        }

        public void SetPowerP1(double _powerP1)
        {
            this.powerP1 = _powerP1;
        }

        public void SetPowerP2(double _powerP2)
        {
            this.powerP2 = _powerP2;
        }

        public void SetPowerP3(double _powerP3)
        {
            this.powerP3 = _powerP3;
        }

        public void SetWorkP1(double _workP1)
        {
            this.workP1 = _workP1;
        }

        public void SetWorkP2(double _workP2)
        {
            this.workP2 = _workP2;
        }

        public void SetWorkP3(double _workP3)
        {
            this.workP3 = _workP3;
        }

        public void SetFrequenzy(double _frequenzy)
        {
            this.frequenzy = _frequenzy;
        }

        public void SetVoltage(double _voltage)
        {
            this.voltage = _voltage;
        }
    }
}