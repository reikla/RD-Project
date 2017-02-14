using Bac1Interfaces.Database;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Bac1Interfaces.Data
{
    /// <summary>
    /// Contains the creator of the smart meter
    /// </summary>
    class MeterManufactorData : IData
    {
        /// <summary>
        /// Id of manufactor
        /// </summary>
        int manufactorId;

        /// <summary>
        /// Describtion of creator
        /// </summary>
        String description;

        public MeterManufactorData()
        {
            DatabaseDriver driver = new DatabaseDriver();
            this.manufactorId = driver.GetUniqueMeterManufactorId();
        }

        override public String[] GetData()
        {
            String[] data = new String[10];

            data[0] = this.manufactorId.ToString();
            data[1] = this.description;

            return data;
        }

        public int FillEmptyMeterManufactor()
        {
            this.description = "FH-Salzburg";

            return this.manufactorId;
        }

        public void SetDescribtion(String _description)
        {
            this.description = _description;
        }

        public String GetDescription()
        {
            return this.description;
        }
    }
}
