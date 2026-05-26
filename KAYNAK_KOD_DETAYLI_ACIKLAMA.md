# 💻 Kaynak Kod Detaylı Açıklama

Tüm custom veri yapılarının ve algoritmaların **satır satır açıklaması**.

---

## 1️⃣ CustomStack - Yığın (LIFO)

**Dosya:** `src/main/java/com/smartwarehouse/datastructure/CustomStack.java`

### Kod:
```java
public class CustomStack<T> {
    private final CustomLinkedList<T> elements = new CustomLinkedList<>();

    public void push(T value) {
        elements.addFirst(value);  // En başa ekle
    }

    public T pop() {
        return elements.removeFirst();  // En baştan çıkar
    }

    public T peek() {
        if (elements.isEmpty()) {
            return null;
        }
        return elements.get(0);  // İlk elemanı göster
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }
}
```

### Açıklama:

**CustomStack İçin Undo işlemi:**

```
undoStack.push(new UndoAction(ADD, null, product))
│
├─ elements.addFirst(item) çağrılır
├─ LinkedList'in başına eklenir
└─ Zaman: O(1)

pop(): 
├─ elements.removeFirst() çağrılır
├─ LinkedList'in başından çıkarır
└─ Zaman: O(1)

VİZÜEL:
push(3) → push(2) → push(1)
┌───┐
│ 1 │ ← peek() = 1
├───┤
│ 2 │
├───┤
│ 3 │
└───┘

KULLANIM: Menu 12 - Undo Last Operation
```

---

## 2️⃣ CustomQueue - FIFO Kuyruğu

**Dosya:** `src/main/java/com/smartwarehouse/datastructure/CustomQueue.java`

### Kod:
```java
public class CustomQueue<T> {
    private final CustomLinkedList<T> elements = new CustomLinkedList<>();

    public void enqueue(T value) {
        elements.add(value);  // Sona ekle
    }

    public T dequeue() {
        return elements.removeFirst();  // Başından çıkar
    }

    public T peek() {
        return elements.isEmpty() ? null : elements.get(0);
    }

    public List<T> toList() {
        return elements.toList();
    }
}
```

### Açıklama - Normal Sipariş:

```
NORMAL SİPARİŞ (Priority 0-6):

enqueue(Shipment #1)
enqueue(Shipment #2)  
enqueue(Shipment #3)

Queue: [#1, #2, #3]

dequeue() → #1 (FIFO - gelen sırasıyla çıkar)
Queue: [#2, #3]

ZAMAN: O(1) enqueue ve dequeue
KULLANIM: Menu 5, 6, 7
```

---

## 3️⃣ CustomPriorityQueue - Öncelik Kuyruğu (MAX HEAP)

**Dosya:** `src/main/java/com/smartwarehouse/datastructure/CustomPriorityQueue.java`

### Kod Özet:
```java
public class CustomPriorityQueue<T extends Comparable<T>> {
    private final List<T> heap = new ArrayList<>();

    public void insert(T item) {
        heap.add(item);
        heapifyUp(heap.size() - 1);  // Yukarı kaydır
    }

    public T extractMax() {
        T max = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);  // Aşağı kaydır
        }
        return max;
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap.get(parent).compareTo(heap.get(index)) >= 0) return;
            swap(parent, index);
            index = parent;
        }
    }
}
```

### Açıklama - Acil Sipariş:

```
ACİL SİPARİŞ (Priority 7-10):

insert(Shipment#1, pri=9)
insert(Shipment#2, pri=7)
insert(Shipment#3, pri=10)

MAX HEAP YAPISI:
        10 (Shipment#3)
       /  \
      9    7
   (S#1)  (S#2)

extractMax() → 10 (En yüksek öncelik HEMEN çıkar!)

ZAMAN: O(log n)
KULLANIM: Menu 8, 9 (ACİL SİPARİŞLER)

AYIRICI ÖZELLİK:
- Queue: FIFO (gelen sırasıyla) 
- PriorityQueue: Priority order (yüksek önce!)
```

---

## 4️⃣ CustomLinkedList - Bağlı Liste

**Dosya:** `src/main/java/com/smartwarehouse/datastructure/CustomLinkedList.java`

### Kod Özet:
```java
private Node<T> head;
private Node<T> tail;
private int size;

public void add(T value) {
    Node<T> node = new Node<>(value);
    if (head == null) {
        head = node;
        tail = node;
    } else {
        tail.next = node;
        tail = node;
    }
    size++;
}

public void addFirst(T value) {
    Node<T> node = new Node<>(value);
    node.next = head;
    head = node;
    size++;
}

public T removeFirst() {
    if (head == null) return null;
    T value = head.value;
    head = head.next;
    size--;
    return value;
}
```

### Açıklama:

```
BAĞLI LİSTE:
head → [1|→] → [2|→] → [3|null] ← tail

add(sona ekle): O(1)
addFirst(başa ekle): O(1)
removeFirst(başından çıkar): O(1)

KULLANILDIĞI YER:
- CustomStack'in arka depo
- CustomQueue'nin arka depo
- StockLog tutmak için
```

---

## 5️⃣ CustomHashTable - Hash Tablosu

**Dosya:** `src/main/java/com/smartwarehouse/datastructure/CustomHashTable.java`

### Kod Özet:
```java
private Entry<K, V>[] table = new Entry[32];

public int hashFunction(K key) {
    return Math.floorMod(key.hashCode(), table.length);
}

public void put(K key, V value) {
    int index = hashFunction(key);
    Entry<K, V> current = table[index];
    
    if (current == null) {
        table[index] = new Entry<>(key, value);
        return;
    }
    
    // Chaining: Collision işleme
    while (current != null) {
        if (Objects.equals(current.key, key)) {
            current.value = value;
            return;
        }
        current = current.next;
    }
    // Zincirin sonuna ekle
}

public V get(K key) {
    Entry<K, V> current = table[hashFunction(key)];
    while (current != null) {
        if (Objects.equals(current.key, key)) {
            return current.value;
        }
        current = current.next;
    }
    return null;
}
```

### Açıklama:

```
HASH TABLE (32 Bucket):

put(1001, Product("Rice", 12.50)):
├─ hashFunction(1001) = 1001 % 32 = 9
├─ table[9] → Entry(1001, Rice)
└─ Zaman: O(1)

put(1033, Product("USB", 4.25)):
├─ hashFunction(1033) = 1033 % 32 = 9 (COLLISION!)
├─ Chaining: table[9] → Entry(1001) → Entry(1033)
└─ Zaman: O(1)

get(1001, ürünü bul):
├─ hashFunction(1001) = 9
├─ table[9]'dan başla
├─ Entry(1001).value = Rice → BULUNDU!
└─ Zaman: O(1)

ZAMAN: O(1) ortalama
KULLANILDIĞI YER: Menu 2, 3, 4, 14 - Hızlı ürün arama
```

---

## 6️⃣ CustomBST - İkili Arama Ağacı

**Dosya:** `src/main/java/com/smartwarehouse/datastructure/CustomBST.java`

### Kod Özet:
```java
private Node<T> root;

public void insert(T value) {
    root = insert(root, value);
}

private Node<T> insert(Node<T> current, T value) {
    if (current == null) {
        return new Node<>(value);
    }
    
    int comparison = comparator.compare(value, current.value);
    if (comparison < 0) {
        current.left = insert(current.left, value);
    } else if (comparison > 0) {
        current.right = insert(current.right, value);
    }
    return current;
}

public List<T> inorderTraversal() {
    List<T> values = new ArrayList<>();
    inorder(root, values);
    return values;
}

private void inorder(Node<T> current, List<T> values) {
    if (current == null) return;
    inorder(current.left, values);   // Sol
    values.add(current.value);       // Kök (SIRALANMIŞ!)
    inorder(current.right, values);  // Sağ
}
```

### Açıklama:

```
AĞAÇ YAPISI (Product ID sırasıyla):
            1001
           /    \
        1002    1005
        /
      1003

Insert sırası: 1001 → 1002 → 1003 → 1005

inorderTraversal() (Sıralı Dolaşma):
├─ Sol ağaca git (1002)
│  ├─ Sol: 1003
│  ├─ Ekle: 1003 ✓
│  ├─ Ekle: 1002 ✓
├─ Ekle: 1001 ✓
├─ Sağ ağaca git (1005)
│  ├─ Ekle: 1005 ✓

RESULT: [1002, 1003, 1001, 1005] (SİRALANMIŞ!)

ZAMAN: O(log n) ortalama
KULLANILDIĞI YER: Menu 10 (BST seçeneği)
```

---

## 7️⃣ CustomGraph - Depo Haritası

**Dosya:** `src/main/java/com/smartwarehouse/datastructure/CustomGraph.java`

### Kod Özet:
```java
Map<T, CustomLinkedList<Edge<T>>> adjacencyList;

public void addVertex(T vertex) {
    adjacencyList.putIfAbsent(vertex, new CustomLinkedList<>());
}

public void addEdge(T from, T to, int weight) {
    adjacencyList.get(from).add(new Edge<>(to, weight));
    adjacencyList.get(to).add(new Edge<>(from, weight));
}

public List<Edge<T>> getEdges(T vertex) {
    return adjacencyList.get(vertex).toList();
}
```

### Açıklama:

```
DEPO HARİTASI:

        Entrance
        /      \
      4/        \6
      /          \
  Shelf-A1    Shelf-A2
      \5        3/
       \       /
        \     /
      PackingStation
            |2
        LoadingDock

Vertices: [Entrance, Shelf-A1, Shelf-A2, PackingStation, LoadingDock]

Edges:
- Entrance ↔ Shelf-A1: 4
- Entrance ↔ Shelf-A2: 6
- Shelf-A1 ↔ PackingStation: 5
- Shelf-A2 ↔ PackingStation: 3
- PackingStation ↔ LoadingDock: 2

KULLANILDIĞI YER: Menu 11 (En Kısa Yol)
```

---

## 8️⃣ Dijkstra Algoritması

**Dosya:** `src/main/java/com/smartwarehouse/algorithm/Dijkstra.java`

### Mantık:

```
DİJKSTRA: Entrance → LoadingDock

Başlangıç:
distance: {Entrance: 0, diğerleri: ∞}
visited: {}

Step 1: Current = Entrance (0)
├─ Shelf-A1: 0+4=4
└─ Shelf-A2: 0+6=6

Step 2: Current = Shelf-A1 (4)
└─ PackingStation: 4+5=9

Step 3: Current = Shelf-A2 (6)
└─ PackingStation: 6+3=9 (eşit)

Step 4: Current = PackingStation (9)
└─ LoadingDock: 9+2=11 ← HEDEF!

SONUÇ:
Path: Entrance → Shelf-A1 → PackingStation → LoadingDock
Distance: 11

ZAMAN: O((V+E) log V)
KULLANILDIĞI YER: Menu 11
```

---

## 📊 Tüm Yapılar Özet

| Veri Yapısı | Amaç | Zaman | Kullanım |
|------------|------|-------|----------|
| Stack | Undo/Redo | O(1) | Menu 12 |
| Queue | Normal Siparişler | O(1) | Menu 5,6,7 |
| PriorityQueue | Acil Siparişler | O(log n) | Menu 8,9 |
| LinkedList | Dinamik Liste | O(1) add/remove | Arka depo |
| HashTable | Hızlı Arama | O(1) | Menu 2,3,4,14 |
| BST | Sıralı Tutma | O(log n) | Menu 10 |
| Graph | Harita | Varies | Veritabanı |
| Dijkstra | En Kısa Yol | O((V+E)logV) | Menu 11 |

---

**Hepsi custom! Hiçbiri Java built-in değil! 💪**