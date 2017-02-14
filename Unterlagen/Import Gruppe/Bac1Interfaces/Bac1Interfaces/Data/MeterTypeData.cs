using Bac1Interfaces.Database;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Bac1Interfaces.Data
{
    /// <summary>
    /// Classifies type of meter
    /// </summary>
    class MeterTypeData : IData
    {
        /// <summary>
        /// Meter type id
        /// </summary>
        int typeId;

        /// <summary>
        /// Describes the meter type (like water, current, Gas)
        /// </summary>
        String description;

        public MeterTypeData()
        {
            DatabaseDriver driver = new DatabaseDriver();
            this.typeId = driver.GetUniqueMeterTypeId();
        }

        override public String[] GetData()
        {
            String[] data = new String[2];

            data[0] = this.typeId.ToString();
            data[1] = this.description;

            return data;
        }

        public int FillEmptyMeterType()
        {
            this.description = "Electrical";
            return this.typeId;
        }

        public void SetDescription(String _describtion)
        {
            this.description = _describtion;
        }

        public String GetDescription()
        {
            return this.description;
        }
    }
}
