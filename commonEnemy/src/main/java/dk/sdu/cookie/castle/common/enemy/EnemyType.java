package dk.sdu.cookie.castle.common.enemy;

import dk.sdu.cookie.castle.common.data.Entityparts.WeaponPart;

public enum EnemyType {
    MELEE(),
    RANGED();

    WeaponPart weaponPart;

    EnemyType() {
        switch (this) {
            case RANGED:
                weaponPart = new WeaponPart(400f, 10f, 5f);
                break;
            case MELEE:
                weaponPart = new WeaponPart(5f, 15f, 8f);
        }
    }

    public WeaponPart getWeaponPart() {
        return weaponPart;
    }
}
