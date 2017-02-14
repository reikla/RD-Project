using Bac1Interfaces.Database;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Bac1Interfaces.Data
{
    /// <summary>
    /// Classifies the communication protocol
    /// </summary>
    class MeterProtocolData : IData
    {
        /// <summary>
        /// protocol id
        /// </summary>
        int protocolId;

        /// <summary>
        /// Describtion of protocol for transmition
        /// </summary>
        String protocol;

        /// <summary>
        /// Describtion of the datascheme for transmition
        /// </summary>
        String dataScheme;

        public MeterProtocolData()
        {
            DatabaseDriver driver = new DatabaseDriver();
            this.protocolId = driver.GetUniqueMeterProtocolId();
        }

        override public String[] GetData()
        {
            String[] data = new String[3];

            data[0] = this.protocolId.ToString();
            data[1] = this.protocol;
            data[2] = this.dataScheme;

            return data;
        }

        public int FillEmptyMeterProtocol()
        {
            this.protocol = "TCP/IP";
            this.dataScheme = "OLAP";
            return this.protocolId;
        }

        public void SetProtocol(String _protocol)
        {
            this.protocol = _protocol;
        }

        public String GetProtocol()
        {
            return this.protocol;
        }

        public void SetDataScheme(String _dataSceme)
        {
            this.dataScheme = _dataSceme;
        }

        public String GetDataScheme()
        {
            return this.dataScheme;
        }
    }
}
