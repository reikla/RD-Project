using Bac1Interfaces.Database;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Bac1Interfaces.Data
{
    class MeterManagementData : IData
    {
        /// <summary>
        /// Id of meter
        /// </summary>
        int meterId;

        /// <summary>
        /// Type of smart meter
        /// </summary>
        int typeId;

        /// <summary>
        /// protocol to communicate with smart meter
        /// </summary>
        int protocolId;

        /// <summary>
        /// Manufactor of the smart meter
        /// </summary>
        int manufactorId;

        /// <summary>
        /// customer, which the smart meter is assigned
        /// </summary>
        int customerId;

        /// <summary>
        /// Describtion of the meter
        /// </summary>
        String description;

        /// <summary>
        /// Serial of meter
        /// </summary>
        String serial;

        /// <summary>
        /// Key to decrypt meter data
        /// </summary>
        String key;

        /// <summary>
        /// sample time
        /// </summary>
        int period;

        /// <summary>
        /// Should the smart meter be read
        /// </summary>
        Boolean active;
         
        /// <summary>
        /// Port to communicate
        /// </summary>
        String port;

        public MeterManagementData()
        {
            DatabaseDriver driver = new DatabaseDriver();
            this.meterId = driver.GetUniqueMeterManagementId();
        }

        override public String[] GetData()
        {
            String[] data = new String[11];

            data[0] = this.meterId.ToString();
            data[1] = this.typeId.ToString();
            data[2] = this.protocolId.ToString();
            data[3] = this.manufactorId.ToString();
            data[4] = this.customerId.ToString();
            data[5] = this.description;
            data[6] = this.serial;
            data[7] = this.key;
            data[8] = this.period.ToString();
            data[9] = this.active.ToString();
            data[10] = this.port;

            return data;
        }

        public void FillEmptyManagementData(int _typeId, int _protocolId, int _manufactorId, int _customerId)
        {
            this.typeId = _typeId;
            this.protocolId = _protocolId;
            this.manufactorId = _manufactorId;
            this.customerId = _customerId;

            this.description = "GREEND-01-005";
            this.serial = "000-000-000";
            this.key = "0192837456";
            this.period = 1;
            this.active = true;
            this.port = "3069";
        }

        public int GetMeterId()
        {
            return this.meterId;
        }

        public void SetTypeId(int _typeId)
        {
            this.typeId = _typeId;
        }

        public int GetTypeId()
        {
            return this.typeId;
        }

        public void SetProtolId(int _protocolId)
        {
            this.protocolId = _protocolId;
        }

        public int GetProtolId()
        {
            return this.protocolId;
        }

        public void SetManufactorId(int _manufactorId)
        {
            this.manufactorId = _manufactorId;
        }

        public int GetManufactorId()
        {
            return this.manufactorId;
        }

        public void SetCustomerId(int _customerId)
        {
            this.customerId = _customerId;
        }

        public int GetCustomerId()
        {
            return this.customerId;
        }

        public void SetDescribtion(String _description)
        {
            this.description = _description;
        }

        public String GetDescription()
        {
            return this.description;
        }

        public void SetSerial(String _serial)
        {
            this.serial = _serial;
        }

        public String GetSerial()
        {
            return this.serial;
        }

        public void SetKey(String _key)
        {
            this.key = _key;
        }

        public String GetKey()
        {
            return this.key;
        }

        public void SetPeriod(int _period)
        {
            this.period = _period;
        }

        public int GetPeriod()
        {
            return this.period;
        }

        public void SetActive(Boolean _active)
        {
            this.active = _active;
        }

        public Boolean GetActive()
        {
            return this.active;
        }

        public void SetPort(String _port)
        {
            this.port = _port;
        }

        public String GetPort()
        {
            return this.port;
        }
    }
}
