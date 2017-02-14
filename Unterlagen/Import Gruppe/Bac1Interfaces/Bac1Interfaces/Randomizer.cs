using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Bac1Interfaces
{
    class Randomizer
    {
        public int getRandomIntBoundries(int start, int end)
        {
            Random rnd = new Random();
            return rnd.Next(start, end);
        }
    }
}
