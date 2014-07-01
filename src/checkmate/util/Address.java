/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.util;

/**
 *
 * @author basme
 */
public final class Address {

    public CellInfo.Rank rank;
    public CellInfo.File file;

    public Address () {}
    
    public Address(CellInfo.Rank rank, CellInfo.File file) {
        this.rank = rank;
        this.file = file;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Address address = (Address) obj;
        return (this.rank == address.rank && this.file == address.file);
    }

}
