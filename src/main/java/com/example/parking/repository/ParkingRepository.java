package com.example.parking.repository;

import com.example.parking.data.entity.ParkingSlot;
import com.example.parking.data.entity.Vehicle;
import com.example.parking.data.entity.VehicleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository     // @Respository is specialized version of the @Component annotation.
public class ParkingRepository {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;
    //Spring would automatically create an EntityManager with the connection we have already provided.
    //private InMemoryDataStore inMemoryDataStore;

    public void createAndUpdateSlot (ParkingSlot parkingSlot) {

        entityManager.merge(parkingSlot); // merge(do crud opr):old entity then update, new create.
        //merge : combination of persist and update, if already exist then it will update the value
        //persist : creating(only create) entity in database ie called for new entity

        //inMemoryDataStore.getSlotTable().put(parkingSlot.getId(), parkingSlot);
        //System.out.println(inMemoryDataStore.getSlotTable());
    }

    //two ways to implement query in jpa 1 TypeCreator Typed query ie via code, 2 namedQuery we can define our entity ourself
    //whereas named query (if to be use)is defined in Entity itself as sql query and here its logic.
    //typed query
    public List<ParkingSlot> getFreeSlotForType(VehicleType vehicleType) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ParkingSlot> criteriaQuery = criteriaBuilder.createQuery(ParkingSlot.class);
        Root<ParkingSlot> parkingSlotRoot = criteriaQuery.from(ParkingSlot.class);

        criteriaQuery.where(
                criteriaBuilder.equal(parkingSlotRoot.get("occupied"), false),
                criteriaBuilder.equal(parkingSlotRoot.get("vehicleType"), vehicleType)
        );
        TypedQuery<ParkingSlot> query = entityManager.createQuery(criteriaQuery).setMaxResults(4);
         return query.getResultList();
        //return new ArrayList<>(inMemoryDataStore.getSlotTable().values());
    }

    //Named Query
    public List<ParkingSlot> getFreeSlotForType2(VehicleType vehicleType) {
        TypedQuery<ParkingSlot> query = entityManager.createNamedQuery("getFreeSlot", ParkingSlot.class);
        query.setParameter("occupied", false);
        query.setParameter("vehicleType", vehicleType);
                return query.getResultList();
    }

    public ParkingSlot getSlotById (String id) {
        return entityManager.find(ParkingSlot.class, id);  //search by id and return object of ParkingSlot
        // return inMemoryDataStore.getSlotTable().get(id);
    }

    public void createVehicle (Vehicle vehicle) {
        entityManager.merge(vehicle);
        //inMemoryDataStore.getVehicleTable()
          //      .put(vehicle.getId(), vehicle);
        //System.out.println(inMemoryDataStore.getVehicleTable());
    }

    public Vehicle getVehicleById (String id) {
        return entityManager.find(Vehicle.class, id);
        //return inMemoryDataStore.getVehicleTable().get(id);
    }

    public void releaseSlot (String id, ParkingSlot parkingSlot) {

        entityManager.remove(getVehicleById(id));
        entityManager.merge(parkingSlot);
    }

    public void deleteSlot (String slotId) {
        entityManager.remove(getSlotById(slotId));
    }

}