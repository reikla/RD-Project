using Bac1Interfaces.Database;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Bac1Interfaces.Data
{
    class CustomerData : IData
    {
        /// <summary>
        /// Id of customer
        /// </summary>
        int customerId;

        /// <summary>
        /// lastname of customer
        /// </summary>
        String lastname;

        /// <summary>
        /// firstname of customer
        /// </summary>
        String firstname;

        /// <summary>
        /// Street/Number/Stair of customer
        /// </summary>
        String street;

        /// <summary>
        /// postal code of customer
        /// </summary>
        String postalCode;

        /// <summary>
        /// home city of customer
        /// </summary>
        String city;

        /// <summary>
        /// Psudoname for anonymisation
        /// </summary>
        String alias;

        /// <summary>
        /// Id of customer from a company
        /// </summary>
        String companyCustomerId;

        /// <summary>
        /// Signature to sign data when sending data
        /// </summary>
        String signature;

        /// <summary>
        /// Key to encrypt data
        /// </summary>
        String key;

        public CustomerData()
        {
            DatabaseDriver driver = new DatabaseDriver();
            this.customerId = driver.GetUniqueCustomerId();
        }

        override public String[] GetData()
        {
            String[] data = new String[10];

            data[0] = this.customerId.ToString();
            data[1] = this.lastname;
            data[2] = this.firstname;
            data[3] = this.street;
            data[4] = this.postalCode;
            data[5] = this.city;
            data[6] = this.alias;
            data[7] = this.companyCustomerId;
            data[8] = this.signature;
            data[9] = this.key;

            return data;
        }

        public int FillEmptyCustomer()
        {
            this.lastname = "Doe";
            this.firstname = "John";
            this.street = "5th Avenue";
            this.postalCode = "5400";
            this.city = "Hallein";
            this.alias = "JD";
            this.companyCustomerId = "ITSJD02";
            this.signature = "JD67";
            this.key = "H05XDK34";

            return this.customerId;
        }

        public int GetCustomerId()
        {
            return this.customerId;
        }

        public String GetLastname()
        {
            return this.lastname;
        }

        public String GetFirstname()
        {
            return this.firstname;
        }

        public String GetStreet()
        {
            return this.street;
        }

        public String GetPostalCode()
        {
            return this.postalCode;
        }

        public String GetCity()
        {
            return this.city;
        }

        public String GetAlias()
        {
            return this.alias;
        }

        public String GetCompanyCustomerId()
        {
            return this.companyCustomerId;
        }

        public String GetSignature()
        {
            return this.signature;
        }

        public String GetKey()
        {
            return this.key;
        }

        public void SetCustomerId(int _customerId)
        {
            this.customerId = _customerId;
        }

        public void SetLastname(String _lastname) //Check those values to correct length
        {
            this.lastname = _lastname;
        }

        public void SetFirstname(String _firstname)
        {
            this.firstname = _firstname;
        }

        public void SetStreet(String _street)
        {
            this.street = _street;
        }

        public void SetPostalCode(String _postalCode)
        {
            this.postalCode = _postalCode;
        }

        public void SetCity(String _city)
        {
            this.city = _city;
        }

        public void SetAlias(String _alias)
        {
            this.alias = _alias;
        }

        public void SetCompanyCustomerId(String _companyCustomerId)
        {
            this.companyCustomerId = _companyCustomerId;
        }

        public void SetSignature(String _signature)
        {
            this.signature = _signature;
        }

        public void SetKey(String _key)
        {
            this.key = _key;
        }

    }
}
