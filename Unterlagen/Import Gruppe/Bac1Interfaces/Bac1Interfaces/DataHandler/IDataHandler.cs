using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Bac1Interfaces.DataHandler
{
    abstract class IDataHandler
    {
        /// <summary>
        /// Path to load the data from
        /// </summary>
        protected String Path;


        protected List<TypeEnum> types;
        protected List<ApplicationEnum> application;

        protected DateTime starttime;
        protected DateTime endtime;

        protected double frequenzy;

        /// <summary>
        /// Functions to traverse Directory and process the Files contained in the directory
        /// </summary>
        public abstract void ParseDirectory();
        protected abstract void processFile(String path);

        /// <summary>
        /// Functions to filter the loaded data by special properties
        /// </summary>
        protected abstract void filterByApplication();
        protected abstract void filterByTime(DateTime minDT, DateTime maxDT);
        protected abstract void filterByType();

        /// <summary>
        /// Fills the DB with the used Meter Management data and some 
        /// meter manufactor, meter protocol, customer and meter type to keep the DB consistent
        /// </summary>
        protected abstract void fillDBWithMeters();

        /// <summary>
        /// Set the path variable
        /// </summary>
        /// <param name="_path">Variable to the path of the data to be loaded</param>
        public void setPath(String _path)
        {
            this.Path = _path;
        }
    }
}
