using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace InterpreterModules
{
    internal class ValueIsNotNumericalException : Exception
    {
        public ValueIsNotNumericalException() {
         
            throw new Exception("Value is not numerical");
        }
    }
}
